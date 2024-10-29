package ch.ifocusit.portfolio.controls;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import ch.ifocusit.portfolio.entities.Portfolio;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PortfolioQueries {

    @Inject
    KafkaStreams streams;

    public Portfolio gePortfolio(String crypto) {
        return null;
    }

    private ReadOnlyKeyValueStore<String, Portfolio> getPortfolioStore() {
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
