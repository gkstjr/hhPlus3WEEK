package hhplus.ecommerce.domain.order.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class OrderCreatedEvent {
    private final long orderId;
    private final long userId;

}
