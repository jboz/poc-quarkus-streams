package ch.ifocusit.orders.executed.controls;

import static org.apache.kafka.streams.StoreQueryParameters.*;
import java.util.List;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
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

    public List<ExecutedOrder> all() {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(store().all(), Spliterator.CONCURRENT), false)
                .map(record -> record.value)
                .toList();
    }

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
