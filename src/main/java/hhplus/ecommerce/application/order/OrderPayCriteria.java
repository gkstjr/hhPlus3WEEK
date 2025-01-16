package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderItemDto;
import hhplus.ecommerce.domain.payment.PayCommand;
import hhplus.ecommerce.domain.user.User;

import java.util.List;

public record OrderPayCriteria(
        User user ,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {

    public OrderCommand toOrderCommand() {
        return new OrderCommand(user, orderItems, issuedCouponId);
    }

    public PayCommand toPayCommand(Long orderId) {
        return new PayCommand(orderId, user);
    }

}
