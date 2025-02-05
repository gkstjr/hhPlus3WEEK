package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.coupon.CouponService;
import hhplus.ecommerce.domain.coupon.UseCouponCommand;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.payment.PayCommand;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.PayInfo;
import hhplus.ecommerce.domain.point.PointService;
import hhplus.ecommerce.domain.point.UsePointCommand;
import hhplus.ecommerce.domain.product.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderPayFacade {

    private final OrderService orderService;
    private final PaymentService paymentService;
    private final DataPlatform dataPlatform;
    private final ProductService productService;
    private final CouponService couponService;
    private final PointService pointService;

    @Transactional
    public OrderPayResult orderPay(OrderPayCriteria criteria) {
        OrderProductsInfo orderProductsInfo = productService.getOrderProducts(criteria.toGetStocksWithProductCommand());
        Order order = orderService.order(orderProductsInfo.toOrderCommand(criteria.user()));
        long discountAmount = couponService.useCoupon(new UseCouponCommand(criteria.issuedCouponId(), criteria.user()));
        PayInfo payInfo = paymentService.pay(new PayCommand(order,criteria.user(),discountAmount));
        long remindPoint = pointService.usePoint(new UsePointCommand(criteria.user(),payInfo.totalAmount()));
        productService.subtractStock(new SubtractStockCommand(orderProductsInfo.orderProducts()));
        dataPlatform.sendOrderData(order);
        return OrderPayResult.of(order,payInfo , discountAmount , remindPoint);
    }
}
