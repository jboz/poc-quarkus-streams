package ch.ifocusit.orders.executed.entities;

import java.time.Instant;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class ExecutedOrder {
    @NonNull
    String crypto;
    @NonNull
    Double quantity;
    @NonNull
    Double unitPrice;

    @NonNull
    @Builder.Default
    Instant timestamp = Instant.now();
}
