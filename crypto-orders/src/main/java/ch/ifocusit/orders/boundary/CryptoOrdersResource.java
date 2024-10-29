package ch.ifocusit.orders.boundary;

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
    @Channel("crypto-orders")
    MutinyEmitter<Order> emitter;

    @POST
    public Uni<Order> create(Order order) {
        return emitter.send(order)
                .onItem().transform(v -> order);
    }
}
