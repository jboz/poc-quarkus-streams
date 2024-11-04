package ch.ifocusit.orders.executed.boundary.crypto;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Crypto {
    @NonNull
    @Default
    UUID id = UUID.randomUUID();
    @NonNull
    String name;
    @NonNull
    Double price; // in dollars

    @NonNull
    Instant timestamp = Instant.now();
}
