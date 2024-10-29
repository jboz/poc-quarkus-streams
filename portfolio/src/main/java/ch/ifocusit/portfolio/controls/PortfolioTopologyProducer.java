package ch.ifocusit.portfolio.controls;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.Stores;
import ch.ifocusit.portfolio.boundary.executed.ExecutedOrder;
import ch.ifocusit.portfolio.entities.Portfolio;
import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class PortfolioTopologyProducer {

        public static final String PORTFOLIO_STORE = "portfolio-store";

        @Produces
        public Topology getPortfolioTopology() {
                var builder = new StreamsBuilder();

                var storeSupplier = Stores.persistentKeyValueStore(PORTFOLIO_STORE);

                var keySerde = Serdes.String();
                var executedOrderSerde = new ObjectMapperSerde<>(ExecutedOrder.class);
                var portfolioSerde = new ObjectMapperSerde<>(Portfolio.class);

                builder.<String, ExecutedOrder>stream("executed-orders", Consumed.with(keySerde, executedOrderSerde))
                                .groupByKey()
                                .aggregate(
                                                Portfolio::new,
                                                (key, order, portfolio) -> portfolio.toBuilder()
                                                                .order(order)
                                                                .build(),
                                                Materialized.<String, Portfolio>as(storeSupplier)
                                                                .withKeySerde(keySerde)
                                                                .withValueSerde(portfolioSerde))
                                .toStream()
                                .to("portfolio", Produced.with(keySerde, portfolioSerde));

                return builder.build();
        }
}
