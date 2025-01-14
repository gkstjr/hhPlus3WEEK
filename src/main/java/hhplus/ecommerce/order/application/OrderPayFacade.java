package hhplus.ecommerce.order.application;

import hhplus.ecommerce.order.application.dataplatform.DataPlatformService;
import hhplus.ecommerce.order.application.dto.OrderPayCriteria;
import hhplus.ecommerce.order.application.dto.OrderPayResult;
import hhplus.ecommerce.order.domain.OrderService;
import hhplus.ecommerce.order.domain.dto.OrderCommand;
import hhplus.ecommerce.order.domain.dto.OrderInfo;
import hhplus.ecommerce.payment.domain.PaymentService;
import hhplus.ecommerce.payment.domain.dto.PayCommand;
import hhplus.ecommerce.payment.domain.dto.PayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderPayFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final DataPlatformService dataPlatformService;

    public OrderPayFacade(OrderService orderService, PaymentService paymentService, DataPlatformService dataPlatformService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.dataPlatformService = dataPlatformService;
    }

    @Transactional
    public OrderPayResult orderPay(OrderPayCriteria criteria) {
        OrderInfo orderInfo = orderService.order(criteria.toOrderCommand());
        PayInfo payInfo = paymentService.pay(criteria.toPayCommand(orderInfo.orderId()));

        try{
            dataPlatformService.sendOrderData(orderInfo);
        }catch (Exception e) {
            log.info("데이터 플랫폼 전송에 실패했습니다.  orderId = " + orderInfo.orderId());
        }

        return OrderPayResult.of(orderInfo,payInfo);
    }
}
