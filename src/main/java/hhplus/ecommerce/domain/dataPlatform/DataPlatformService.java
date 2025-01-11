package hhplus.ecommerce.domain.dataPlatform;

import hhplus.ecommerce.domain.order.dto.OrderCommand;
import hhplus.ecommerce.domain.order.dto.OrderInfo;
import hhplus.ecommerce.domain.order.model.Order;
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

