package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderItemDto;
import hhplus.ecommerce.domain.payment.PayCommand;

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
