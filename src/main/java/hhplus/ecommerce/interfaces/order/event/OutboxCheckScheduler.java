package hhplus.ecommerce.interfaces.order.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ecommerce.application.order.event.OrderEvent;
import hhplus.ecommerce.application.order.event.OrderOutbox.OrderOutboxStatus;
import hhplus.ecommerce.application.order.event.OrderProducer;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
public class OutboxCheckScheduler {

    private final OrderRepository orderRepository;
    private final OrderProducer producer;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    public void verifyOutboxStatus(){
        orderRepository.findAllByStatus(OrderOutboxStatus.PENDING)
                .stream()
                .filter(event -> ChronoUnit.MINUTES.between(event.getCreatedAt(), LocalDateTime.now()) > 5)
                .forEach(event -> {
                    try {
                        OrderEvent orderEvent = objectMapper.readValue(event.getPayload(), OrderEvent.class);
                        producer.sendMessage(orderEvent);
                    } catch (JsonProcessingException e) {
                        throw  new BusinessException(ErrorCode.ORDER_JSON_PARSE_ERROR);
                    }
                });
    }
}