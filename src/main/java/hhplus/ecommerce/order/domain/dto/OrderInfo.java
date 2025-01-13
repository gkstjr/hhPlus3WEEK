package hhplus.ecommerce.order.domain.dto;

import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.order.domain.model.Order;
import hhplus.ecommerce.order.domain.model.OrderProduct;

import java.util.List;

public record OrderInfo(
        long orderId,
        long totalAmount,
        long userId,

        IssuedCoupon issuedCoupon,
        List<OrderProduct> orderProducts

) {
    public static OrderInfo from(Order order) {
        return new OrderInfo(order.getId(), order.getTotalAmount(),order.getUser().getId() , order.getIssuedCoupon(), order.getOrderProductList());
    }
}
