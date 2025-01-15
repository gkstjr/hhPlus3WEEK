package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPlatform {

    @Async
    public void sendOrderData(OrderInfo orderInfo) {
            try {
                log.info("데이터 플랫폼에 비동기 전송: {}" , orderInfo);
            }catch (Exception e) {
                log.error("데이터 플랫폼 전송에 실패했습니다.  orderId ={}",orderInfo.orderId());
            }
    }
}