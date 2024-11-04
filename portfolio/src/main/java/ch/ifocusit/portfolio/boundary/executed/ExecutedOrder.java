package ch.ifocusit.portfolio.boundary.executed;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder
@Value
public class ExecutedOrder {
    @Builder.Default
    UUID id = UUID.randomUUID();

    @NonNull
    String crypto;
    @NonNull
    Double quantity;
    @NonNull
    Double unitPrice;

    UUID valueId;
    UUID orderId;

    @NonNull
    @Builder.Default
    Instant timestamp = Instant.now();
}
