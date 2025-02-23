package hhplus.ecommerce.application.order.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class OrderOutboxManager {
    private final ObjectMapper objectMapper;
    private final OrderRepository orderRepository;

    @Transactional
    public void saveOutbox(OrderEvent event) {
        String payload;
        try {
            payload = objectMapper.writeValueAsString(event);
        }catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.ORDER_JSON_PARSE_ERROR);
        }

        orderRepository.saveOutbox(OrderOutbox.create(payload , event.getEventId()));
    }
}
