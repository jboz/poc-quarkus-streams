package ch.ifocusit.orders.executed.controls;

import static org.apache.kafka.streams.StoreQueryParameters.*;
import static org.awaitility.Awaitility.*;
import java.time.Duration;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ifocusit.orders.executed.boundary.crypto.Crypto;
import ch.ifocusit.orders.executed.boundary.orders.Order;
import ch.ifocusit.orders.executed.entities.ExecutedOrder;
import io.quarkus.kafka.client.serialization.ObjectMapperDeserializer;
import io.quarkus.kafka.client.serialization.ObjectMapperSerializer;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.kafka.InjectKafkaCompanion;
import io.quarkus.test.kafka.KafkaCompanionResource;
import io.smallrye.reactive.messaging.kafka.companion.KafkaCompanion;
import jakarta.inject.Inject;

@QuarkusTest
@QuarkusTestResource(KafkaCompanionResource.class)
public class ExecutedOrdersTopologyTest {

        @InjectKafkaCompanion
        KafkaCompanion companion;

        @Inject
        KafkaStreams streams;

        @BeforeEach
        public void setUp() {
                streams.start();

                companion.registerSerde(
                                Crypto.class,
                                new ObjectMapperSerializer<>(),
                                new ObjectMapperDeserializer<>(Crypto.class));
                companion.registerSerde(
                                Order.class,
                                new ObjectMapperSerializer<>(),
                                new ObjectMapperDeserializer<>(Order.class));
        }

        @Test
        void topology() {
                Crypto value = Crypto.builder().name("BTC").price(10_000.0).build();
                Order order = Order.builder().crypto("BTC").quantity(1.0).build();

                companion.produce(Crypto.class)
                                .fromRecords(new ProducerRecord<>("crypto-values", "BTC", value));

                companion.produce(Order.class)
                                .fromRecords(new ProducerRecord<>("crypto-orders", "BTC", order));

                await()
                                .atMost(Duration.ofSeconds(5))
                                .untilAsserted(
                                                () -> {
                                                        ReadOnlyKeyValueStore<String, ExecutedOrder> store =
                                                                        streams.store(fromNameAndType("executed-orders-store",
                                                                                        QueryableStoreTypes.keyValueStore()));
                                                        Assert.assertNotNull(store);
                                                        try {
                                                                var executed = store.get("BTC");
                                                                Assert.assertNotNull(executed);
                                                                Assert.assertNotNull(executed.getId());
                                                                Assert.assertNotNull(executed.getTimestamp());
                                                                Assert.assertEquals(executed.getCrypto(), order.getCrypto());
                                                                Assert.assertEquals(executed.getOrderId(), order.getId());
                                                                Assert.assertEquals(executed.getValueId(), value.getId());
                                                                Assert.assertEquals(executed.getQuantity(), order.getQuantity());
                                                                Assert.assertEquals(executed.getUnitPrice(), value.getPrice());
                                                        } catch (Exception e) {
                                                                Assert.fail("The store is not ready");
                                                        }
                                                });
        }
}
