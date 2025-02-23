package hhplus.ecommerce.application.order.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ecommerce.application.order.DataPlatform;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderConsumer {
    private final DataPlatform dataPlatform;
    private final OrderRepository orderRepository;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "order-create", groupId = "order-outbox-validator-group")
    @Transactional
    public void validateOrderOutbox(String message) {
        try{
            OrderEvent event = objectMapper.readValue(message,OrderEvent.class);

            orderRepository.findByEventId(event.getEventId()).ifPresent(OrderOutbox::checkPublished);
        }catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.ORDER_JSON_PARSE_ERROR);
        }
    }

    @KafkaListener(topics = "order-create", groupId = "order-data-platform-group")
    @Transactional
    public void sendDataPlatform(String message) {
        try {
            OrderEvent event = objectMapper.readValue(message, OrderEvent.class);

            dataPlatform.sendOrderData(event);

        } catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.ORDER_JSON_PARSE_ERROR);
        }
    }
}
