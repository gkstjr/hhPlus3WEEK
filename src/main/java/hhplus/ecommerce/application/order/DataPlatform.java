package hhplus.ecommerce.application.order;

import hhplus.ecommerce.application.order.event.OrderEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPlatform {

    public void sendOrderData(OrderEvent event) {
            try {
                log.info("데이터 플랫폼에 전송 성공: 주문Id : {} , 회원ID : {}" , event.getOrderId() , event.getUserId());
            }catch (Exception e) {
                log.error("데이터 플랫폼 전송에 실패했습니다.  주문Id : {} , 회원ID : {}",event.getOrderId(),event.getUserId());
            }
    }
}