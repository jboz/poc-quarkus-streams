package ch.ifocusit.values.entities;

import java.time.Instant;
import lombok.NonNull;
import lombok.Value;

@Value
public class Crypto {
    @NonNull
    String name;
    @NonNull
    Double price; // in dollars

    @NonNull
    Instant timestamp = Instant.now();
}
