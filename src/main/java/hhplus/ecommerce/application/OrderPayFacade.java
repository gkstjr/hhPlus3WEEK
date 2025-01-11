package hhplus.ecommerce.application;

import hhplus.ecommerce.application.dto.OrderPayCriteria;
import hhplus.ecommerce.application.dto.OrderPayResult;
import hhplus.ecommerce.domain.dataPlatform.DataPlatformService;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.dto.OrderCommand;
import hhplus.ecommerce.domain.order.dto.OrderInfo;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.dto.PayCommand;
import hhplus.ecommerce.domain.payment.dto.PayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

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

    public OrderPayResult orderPay(OrderPayCriteria criteria) {
        OrderInfo orderInfo = orderService.order(new OrderCommand(criteria.userId(),criteria.orderItems(), criteria.issuedCouponId()));
        PayInfo payInfo = paymentService.pay(new PayCommand(orderInfo.orderId(),criteria.userId()));

        try{
            dataPlatformService.sendOrderData(orderInfo);
        }catch (Exception e) {
            log.info("데이터 플랫폼 전송에 실패했습니다!  orderId = " + orderInfo.orderId());
        }

        return OrderPayResult.of(orderInfo,payInfo);
    }
}
