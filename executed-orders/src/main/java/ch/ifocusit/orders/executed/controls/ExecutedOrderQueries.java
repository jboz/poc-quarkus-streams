package ch.ifocusit.orders.executed.controls;

import static org.apache.kafka.streams.StoreQueryParameters.*;
import java.util.Optional;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import ch.ifocusit.orders.executed.entities.ExecutedOrder;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class ExecutedOrderQueries {

    @Inject
    KafkaStreams streams;

    public Optional<ExecutedOrder> byCrypto(String crypto) {
        return Optional.ofNullable(store().get(crypto));
    }

    private ReadOnlyKeyValueStore<String, ExecutedOrder> store() {
        while (true) {
            try {
                return streams.store(fromNameAndType("executed-orders-store",
                        QueryableStoreTypes.keyValueStore()));
            } catch (Exception e) {
                // ignore because store is not ready
            }
        }
    }
}
