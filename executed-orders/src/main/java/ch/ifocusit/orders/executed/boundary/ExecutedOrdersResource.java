package ch.ifocusit.orders.executed.boundary;

import java.util.List;
import ch.ifocusit.orders.executed.controls.ExecutedOrderQueries;
import ch.ifocusit.orders.executed.entities.ExecutedOrder;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/orders/executed")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExecutedOrdersResource {

    @Inject
    ExecutedOrderQueries queries;

    @GET
    public List<ExecutedOrder> orders() {
        return queries.all();
    }

    @GET
    @Path("/{crypto}")
    public ExecutedOrder order(@PathParam("crypto") String crypto) {
        return queries.byCrypto(crypto)
                .orElseThrow(() -> new NotFoundException("No order for this crypto: " + crypto));
    }
}
