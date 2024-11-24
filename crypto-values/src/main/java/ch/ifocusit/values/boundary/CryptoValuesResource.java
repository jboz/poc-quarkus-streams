package ch.ifocusit.values.boundary;

import java.time.LocalDateTime;
import java.util.UUID;
import org.eclipse.microprofile.reactive.messaging.Channel;
import ch.ifocusit.values.entities.Crypto;
import io.smallrye.mutiny.Uni;
import io.smallrye.reactive.messaging.MutinyEmitter;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/api/crypto/values")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CryptoValuesResource {

    @Inject
    @Channel("values")
    MutinyEmitter<Crypto> emitter;

    @POST
    public Uni<Crypto> create(Crypto crypto) {
        Crypto payload = Crypto.newBuilder(crypto)
                .setId(UUID.randomUUID())
                .setTimestamp(LocalDateTime.now())
                .build();
        return emitter.send(payload)
                .onItem().transform(v -> payload);
    }
}
