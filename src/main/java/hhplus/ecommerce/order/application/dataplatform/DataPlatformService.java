package hhplus.ecommerce.order.application.dataplatform;

import hhplus.ecommerce.order.domain.dto.OrderInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DataPlatformService {

    @Async
    public void sendOrderData(OrderInfo orderInfo) {
            // 비동기 전송 로직
               log.info("데이터 플랫폼에 비동기 전송: " + orderInfo);
        }
    }

