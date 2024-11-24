package ch.ifocusit.orders.boundary;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.eclipse.microprofile.reactive.messaging.spi.Connector;
import org.junit.jupiter.api.Test;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import io.smallrye.reactive.messaging.memory.InMemoryConnector;
import jakarta.inject.Inject;

@QuarkusTest
@TestHTTPEndpoint(CryptoOrdersResource.class)
public class CryptoOrdersResourceTest {

    @Inject
    @Connector("smallrye-in-memory")
    InMemoryConnector connector;

    @Test
    public void testHelloEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "crypto": "BTC",
                            "quantity": 2.5
                        }
                        """)
                .when().post()
                .then().statusCode(200)
                .and()
                .body("id", notNullValue())
                .body("timestamp", notNullValue())
                .body("crypto", is("BTC"))
                .body("quantity", is(2.5F));

        // InMemorySink<Order> out = connector.sink("orders");

        // await().until(out::received, t -> t.size() == 1);

        // var record = out.received().get(0).getPayload();
        // assertNotNull(record);
        // assertNotNull(record.getId());
        // assertNotNull(record.getTimestamp());
        // assertEquals(record.getCrypto(), "BTC");
        // assertEquals(record.getQuantity(), 2.5, 0);
    }
}
