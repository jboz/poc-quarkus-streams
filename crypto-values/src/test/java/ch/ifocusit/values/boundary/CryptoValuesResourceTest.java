package ch.ifocusit.values.boundary;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@TestHTTPEndpoint(CryptoValuesResource.class)
public class CryptoValuesResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
                .contentType(ContentType.JSON)
                .body("""
                        {
                            "name": "BTC",
                            "price": 10.2456
                        }
                        """)
                .when().post()
                .then().statusCode(200)
                .and()
                .body("id", notNullValue())
                .body("timestamp", notNullValue())
                .body("name", is("BTC"))
                .body("price", is(10.2456F));
    }
}
