package ch.ifocusit.orders.executed.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Builder(toBuilder = true)
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

    @NonNull
    UUID valueId;
    @NonNull
    UUID orderId;

    @Builder.Default
    Instant timestamp = Instant.now();

    public static ExecutedOrderBuilder toBuilder(ExecutedOrder previous) {
        return previous == null ? ExecutedOrder.builder() : previous.toBuilder();
    }
}
