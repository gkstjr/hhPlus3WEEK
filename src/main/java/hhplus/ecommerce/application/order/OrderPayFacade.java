package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.OrderInfo;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.PayInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class OrderPayFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final DataPlatform dataPlatform;

    public OrderPayFacade(OrderService orderService, PaymentService paymentService, DataPlatform dataPlatform) {
        this.orderService = orderService;
        this.paymentService = paymentService;
        this.dataPlatform = dataPlatform;
    }

    @Transactional
    public OrderPayResult orderPay(OrderPayCriteria criteria) {
        OrderInfo orderInfo = orderService.order(criteria.toOrderCommand());
        PayInfo payInfo = paymentService.pay(criteria.toPayCommand(orderInfo.orderId()));

        dataPlatform.sendOrderData(orderInfo);
        return OrderPayResult.of(orderInfo,payInfo);
    }
}
