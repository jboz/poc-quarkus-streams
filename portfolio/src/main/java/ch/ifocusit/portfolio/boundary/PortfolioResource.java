package ch.ifocusit.portfolio.boundary;

import org.eclipse.microprofile.reactive.messaging.Channel;
import ch.ifocusit.portfolio.boundary.executed.ExecutedOrder;
import ch.ifocusit.portfolio.entities.Portfolio;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/portfolio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PortfolioResource {

    @Channel("executed-orders")
    Multi<ExecutedOrder> executed;

    @GET
    public Uni<Portfolio> portfolio() {
        return null; // TODO
    }

    @GET
    @Path("/executed")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<ExecutedOrder> executed() {
        return executed;
    }
}
