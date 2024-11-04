package ch.ifocusit.orders.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.NonNull;
import lombok.Value;

@Value
public class Order {
    UUID id = UUID.randomUUID();

    @NonNull
    String crypto;
    @NonNull
    Double quantity; // can be positive or negative

    @NonNull
    Instant timestamp = Instant.now();
}
