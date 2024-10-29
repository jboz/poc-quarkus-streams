package ch.ifocusit.orders.executed.controls;

import java.time.Duration;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.StreamJoined;
import ch.ifocusit.orders.executed.boundary.crypto.Crypto;
import ch.ifocusit.orders.executed.boundary.orders.Order;
import ch.ifocusit.orders.executed.entities.ExecutedOrder;
import io.quarkus.kafka.client.serialization.ObjectMapperSerde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class ExecutedOrdersTopology {

        @Produces
        public Topology getPortfolioTopology() {
                var builder = new StreamsBuilder();

                var valueSerde = new ObjectMapperSerde<>(Crypto.class);
                var orderSerde = new ObjectMapperSerde<>(Order.class);
                var keySerde = Serdes.String();
                var executedOrderSerde = new ObjectMapperSerde<>(ExecutedOrder.class);

                var values = builder.stream(
                                "crypto-values",
                                Consumed.with(keySerde, valueSerde))
                                // use crypto name as key
                                .map((key, payload) -> KeyValue.pair(payload.getName(), payload));

                var orders = builder.stream(
                                "crypto-orders",
                                Consumed.with(keySerde, orderSerde))
                                // use crypto name as key
                                .map((key, payload) -> KeyValue.pair(payload.getCrypto(), payload));

                // join values and orders
                values.join(orders,
                                (value, order) -> ExecutedOrder.builder()
                                                .crypto(value.getName())
                                                .unitPrice(value.getPrice())
                                                .quantity(order.getQuantity())
                                                .build(),
                                // and in a window of 2 minutes
                                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofMinutes(2)),
                                StreamJoined.with(keySerde, valueSerde, orderSerde))
                                // .groupByKey() // keep the latest order per crypto
                                // .reduce((a, b) -> b)
                                // .toStream()
                                .to("executed-orders", Produced.with(keySerde, executedOrderSerde));

                return builder.build();
        }

        public static record Pair(Crypto left, Order right) {
        }
}
