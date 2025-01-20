package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.domain.coupon.IssuedCoupon;

import java.util.List;

public record OrderInfo(
        long orderId,
        long totalAmount,
        long userId,
        List<OrderProduct> orderProducts

) {
    public static OrderInfo from(Order order) {
        return new OrderInfo(order.getId(), order.getTotalAmount(),order.getUser().getId() , order.getOrderProductList());
    }
}
