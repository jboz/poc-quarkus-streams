package ch.ifocusit.orders.executed.boundary.config;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import io.quarkus.jackson.JacksonMixin;
import io.quarkus.kafka.client.runtime.devui.model.Order;

@JacksonMixin(Order.class)
@JsonTypeInfo(use = Id.SIMPLE_NAME, property = "type")
public interface OrderMixin {
}
