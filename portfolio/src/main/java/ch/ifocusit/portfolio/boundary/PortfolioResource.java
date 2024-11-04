package ch.ifocusit.portfolio.boundary;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.eclipse.microprofile.reactive.messaging.Channel;
import ch.ifocusit.portfolio.boundary.executed.ExecutedOrder;
import ch.ifocusit.portfolio.entities.Portfolio;
import io.quarkiverse.resteasy.problem.HttpProblem;
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
        try {
            Stream<KeyValue<String, Portfolio>> stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(
                    store.all(), Spliterator.CONCURRENT), false);

            return Multi.createFrom().items(stream).onItem().transform(v -> v.value);
        } catch (InvalidStateStoreException e) {
            throw HttpProblem.builder()
                    .withStatus(422)
                    .withTitle("Invalid state store")
                    .withDetail(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/executed")
    @Produces(MediaType.SERVER_SENT_EVENTS)
    public Multi<ExecutedOrder> executed() {
        return executed;
    }
}
