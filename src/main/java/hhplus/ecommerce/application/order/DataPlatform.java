package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@Slf4j
public class DataPlatform {

    @Async
    @TransactionalEventListener //AFTER_COMMIT 기본값이므로 설정 생략
    public void sendOrderData(OrderCreatedEvent event) {
            try {
                log.info("데이터 플랫폼에 전송 성공: 주문Id : {} , 회원ID : {}" , event.getOrderId() , event.getUserId());
            }catch (Exception e) {
                log.error("데이터 플랫폼 전송에 실패했습니다.  주문Id : {} , 회원ID : {}",event.getOrderId(),event.getUserId());
            }
    }
}