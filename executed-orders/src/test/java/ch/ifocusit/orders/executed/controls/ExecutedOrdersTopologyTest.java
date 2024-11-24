package ch.ifocusit.orders.executed.controls;

import static org.awaitility.Awaitility.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.streams.KafkaStreams;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ch.ifocusit.orders.entities.Order;
import ch.ifocusit.values.entities.Crypto;
import io.confluent.kafka.streams.serdes.avro.SpecificAvroSerde;
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
                companion.registerSerde(Crypto.class, new SpecificAvroSerde<Crypto>());
                companion.registerSerde(Order.class, new SpecificAvroSerde<Order>());
        }

        @Test
        void topology() {
                Crypto value = Crypto.newBuilder().setName("BTC").setPrice(10_000.0)
                                .setId(UUID.randomUUID()).setTimestamp(LocalDateTime.now()).build();

                Order order = Order.newBuilder().setCrypto("BTC").setQuantity(1.0)
                                .setId(UUID.randomUUID()).setTimestamp(LocalDateTime.now()).build();

                companion.produce(Crypto.class)
                                .fromRecords(new ProducerRecord<>("crypto-values", "BTC", value));

                companion.produce(Order.class)
                                .fromRecords(new ProducerRecord<>("crypto-orders", "BTC", order));

                await()
                                .atMost(Duration.ofSeconds(5))
                                .untilAsserted(
                                                () -> {
                                                        // try {
                                                        // ReadOnlyKeyValueStore<String, ExecutedOrder> store =
                                                        // streams.store(fromNameAndType("executed-orders-store",
                                                        // QueryableStoreTypes.keyValueStore()));
                                                        // Assert.assertNotNull(store);

                                                        // var executed = store.get("BTC");
                                                        // Assert.assertNotNull(executed);
                                                        // Assert.assertNotNull(executed.getId());
                                                        // Assert.assertNotNull(executed.getTimestamp());
                                                        // Assert.assertEquals(executed.getCrypto(), order.getCrypto());
                                                        // Assert.assertEquals(executed.getOrderId(), order.getId());
                                                        // Assert.assertEquals(executed.getValueId(), value.getId());
                                                        // Assert.assertEquals(executed.getQuantity(), order.getQuantity(), 0D);
                                                        // Assert.assertEquals(executed.getUnitPrice(), value.getPrice(), 0D);
                                                        // } catch (Exception e) {
                                                        // Assert.fail("The store is not ready");
                                                        // }
                                                });
        }
}
