package hhplus.ecommerce.application.order.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class OrderEvent {
    private final String eventId; //UUID 자동생성
    private final long orderId;
    private final long userId;

    public OrderEvent(Long orderId, Long userId) {
        this.eventId = UUID.randomUUID().toString();
        this.orderId = orderId;
        this.userId = userId;
    }
}
