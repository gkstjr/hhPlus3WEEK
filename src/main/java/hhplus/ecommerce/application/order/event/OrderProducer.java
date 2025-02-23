package hhplus.ecommerce.application.order.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class OrderProducer {

    private final KafkaTemplate<String , String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    @Transactional
    public void sendMessage(OrderEvent event) {
        try {
            String message = objectMapper.writeValueAsString(event);
            kafkaTemplate.send("order-create",message);
        }catch (JsonProcessingException e) {
            throw new BusinessException(ErrorCode.ORDER_JSON_PARSE_ERROR);
        }catch (Exception e) {
            throw new BusinessException(ErrorCode.Kafka_NETWORT_FAILED);
        }
    }
}
