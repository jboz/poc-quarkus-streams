package ch.ifocusit.portfolio.boundary;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.eclipse.microprofile.reactive.messaging.Channel;
import ch.ifocusit.portfolio.boundary.executed.ExecutedOrder;
import ch.ifocusit.portfolio.entities.Portfolio;
import io.smallrye.mutiny.Multi;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/portfolio")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PortfolioResource {

    @Inject
    ReadOnlyKeyValueStore<String, Portfolio> store;

    @Channel("executed-orders")
    Multi<ExecutedOrder> executed;

    @GET
    public Multi<Portfolio> portfolio() {
        return Multi.createFrom()
                .items(StreamSupport.stream(Spliterators.spliteratorUnknownSize(store.all(), Spliterator.CONCURRENT), false))
                .onItem().transform(v -> v.value);
    }

    @GET
    @Path("/executed")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<ExecutedOrder> executed() {
        return executed;
    }
}
