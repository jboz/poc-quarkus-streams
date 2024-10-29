package ch.ifocusit.orders.executed.boundary;

import java.util.Optional;
import ch.ifocusit.orders.executed.controls.ExecutedOrderQueries;
import ch.ifocusit.orders.executed.entities.ExecutedOrder;
import jakarta.inject.Inject;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/api/orders/executed")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExecutedOrdersResource {

    @Inject
    ExecutedOrderQueries queries;

    @GET
    public Optional<ExecutedOrder> order(@NotBlank @QueryParam("crypto") String crypto) {
        return queries.byCrypto(crypto);
    }
}
