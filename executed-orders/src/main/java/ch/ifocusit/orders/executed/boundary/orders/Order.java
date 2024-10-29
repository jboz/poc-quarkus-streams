package ch.ifocusit.orders.executed.boundary.orders;

import java.time.Instant;
import lombok.NonNull;
import lombok.Value;

@Value
public class Order {
    @NonNull
    String crypto;
    @NonNull
    Double quantity;

    @NonNull
    Instant timestamp = Instant.now();
}
