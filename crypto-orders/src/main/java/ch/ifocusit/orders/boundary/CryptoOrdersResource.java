package ch.ifocusit.orders.boundary;

import java.time.LocalDateTime;
import java.util.UUID;
import org.eclipse.microprofile.reactive.messaging.Channel;
import ch.ifocusit.orders.entities.Order;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/crypto/orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CryptoOrdersResource {

    @Inject
    @Channel("orders")
    MutinyEmitter<Order> emitter;

    @POST
    public Uni<Order> create(Order order) {
        Order payload = Order.newBuilder(order)
                .setId(UUID.randomUUID())
                .setTimestamp(LocalDateTime.now())
                .build();
        return emitter.send(payload)
                .onItem().transform(v -> payload);
    }
}
