package ch.ifocusit.orders.entities;

import java.time.Instant;
import lombok.NonNull;
import lombok.Value;

@Value
public class Order {
    @NonNull
    String crypto;
    @NonNull
    Double quantity; // can be positive or negative

    @NonNull
    Instant timestamp = Instant.now();
}
