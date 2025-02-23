package hhplus.ecommerce.interfaces.order.event;


import hhplus.ecommerce.application.order.event.OrderEvent;
import hhplus.ecommerce.application.order.event.OrderProducer;
import hhplus.ecommerce.application.order.event.OrderOutboxManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import static org.springframework.transaction.event.TransactionPhase.*;
@Component
@RequiredArgsConstructor
public class OrderEventListener {
    private final OrderOutboxManager orderOutboxManager;
    private final OrderProducer orderProducer;
    @TransactionalEventListener(phase = BEFORE_COMMIT)
    public void saveOutbox(OrderEvent event) {
        orderOutboxManager.saveOutbox(event);
    }

    @TransactionalEventListener(phase = AFTER_COMMIT)
    public void sendOrderInfo(OrderEvent event) {
        orderProducer.sendMessage(event);
    }
}
