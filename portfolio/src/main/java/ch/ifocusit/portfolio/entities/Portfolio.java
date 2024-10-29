package ch.ifocusit.portfolio.entities;

import java.util.List;
import ch.ifocusit.portfolio.boundary.executed.ExecutedOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Portfolio {
    @Singular
    List<ExecutedOrder> orders;
}
