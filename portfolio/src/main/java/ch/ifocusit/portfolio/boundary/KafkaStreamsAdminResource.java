package ch.ifocusit.portfolio.boundary;

import org.apache.kafka.streams.KafkaStreams;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Path("/api/admin/kafka")
public class KafkaStreamsAdminResource {

    @Inject
    KafkaStreams kafkaStreams;

    @POST
    @Path("/reset")
    public void reset() {
        kafkaStreams.close();
        kafkaStreams.cleanUp();
        kafkaStreams.start();
    }
}
