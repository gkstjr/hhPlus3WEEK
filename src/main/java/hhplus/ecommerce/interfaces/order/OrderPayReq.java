package hhplus.ecommerce.interfaces.order;

import hhplus.ecommerce.application.order.OrderPayCriteria;
import hhplus.ecommerce.domain.order.OrderItemDto;

import java.util.List;

public record OrderPayReq(
        Long userId,
        List<OrderItemDto> orderItems,
        long issuedCouponId
) {

    public OrderPayCriteria toCriteria() {
        return new OrderPayCriteria(userId, orderItems, issuedCouponId);
    }
}
