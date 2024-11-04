package ch.ifocusit.portfolio.controls;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import ch.ifocusit.portfolio.entities.Portfolio;
import io.quarkiverse.resteasy.problem.HttpProblem;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class PortfolioStore {

    @Inject
    KafkaStreams streams;

    @Produces
    public ReadOnlyKeyValueStore<String, Portfolio> store() {
        try {
            return streams.store(StoreQueryParameters.fromNameAndType(PortfolioTopologyProducer.PORTFOLIO_STORE,
                    QueryableStoreTypes.keyValueStore()));
        } catch (Exception e) {
            throw HttpProblem.builder()
                    .withStatus(422)
                    .withTitle("Empty stream.")
                    .build();
        }
    }
}
