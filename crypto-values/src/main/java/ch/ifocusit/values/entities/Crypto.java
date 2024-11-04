package ch.ifocusit.values.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;

@Value
public class Crypto {
    UUID id = UUID.randomUUID();

    @NonNull
    String name;
    @NonNull
    Double price; // in dollars

    @NonNull
    Instant timestamp = Instant.now();
}
