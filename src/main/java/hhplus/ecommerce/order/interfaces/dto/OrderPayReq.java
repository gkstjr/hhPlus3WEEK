package hhplus.ecommerce.order.interfaces.dto;

import hhplus.ecommerce.order.application.dto.OrderPayCriteria;
import hhplus.ecommerce.order.domain.dto.OrderItemDto;

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
