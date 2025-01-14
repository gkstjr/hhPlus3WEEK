package hhplus.ecommerce.order.application.dto;

import hhplus.ecommerce.order.domain.dto.OrderCommand;
import hhplus.ecommerce.order.domain.dto.OrderItemDto;
import hhplus.ecommerce.payment.domain.dto.PayCommand;

import java.util.List;

public record OrderPayCriteria(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {

    public OrderCommand toOrderCommand() {
        return new OrderCommand(userId, orderItems, issuedCouponId);
    }

    public PayCommand toPayCommand(Long orderId) {
        return new PayCommand(orderId, userId);
    }

}
