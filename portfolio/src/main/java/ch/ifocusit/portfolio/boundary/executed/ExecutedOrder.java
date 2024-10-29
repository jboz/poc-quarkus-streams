package ch.ifocusit.portfolio.boundary.executed;

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
