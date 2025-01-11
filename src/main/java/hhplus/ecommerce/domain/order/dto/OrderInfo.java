package hhplus.ecommerce.domain.order.dto;

import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.order.model.OrderProduct;
import org.aspectj.weaver.ast.Or;

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
