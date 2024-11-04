package ch.ifocusit.orders.executed.boundary.orders;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class Order {
    @Default
    UUID id = UUID.randomUUID();
    @NonNull
    String crypto;
    @NonNull
    Double quantity;

    @NonNull
    Instant timestamp = Instant.now();
}
