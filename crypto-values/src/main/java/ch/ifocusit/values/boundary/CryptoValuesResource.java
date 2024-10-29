package ch.ifocusit.values.boundary;

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
    @Channel("crypto-values")
    MutinyEmitter<Crypto> emitter;

    @POST
    public Uni<Crypto> create(Crypto crypto) {
        return emitter.send(crypto)
                .onItem().transform(v -> crypto);
    }
}
