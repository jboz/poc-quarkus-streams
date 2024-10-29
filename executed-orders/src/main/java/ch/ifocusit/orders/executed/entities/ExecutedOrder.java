package ch.ifocusit.orders.executed.entities;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExecutedOrder {
    String crypto;
    Double quantity;
    Double unitPrice;

    @Builder.Default
    Instant timestamp = Instant.now();
}
