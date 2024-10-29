package ch.ifocusit.portfolio.controls;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import ch.ifocusit.portfolio.entities.Portfolio;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;

public class PortfolioStore {

    @Inject
    KafkaStreams streams;

    @Produces
    public ReadOnlyKeyValueStore<String, Portfolio> store() {
        while (true) {
            try {
                return streams.store(StoreQueryParameters.fromNameAndType(PortfolioTopologyProducer.PORTFOLIO_STORE,
                        QueryableStoreTypes.keyValueStore()));
            } catch (InvalidStateStoreException e) {
                // ignore, store not ready yet
            }
        }
    }
}
