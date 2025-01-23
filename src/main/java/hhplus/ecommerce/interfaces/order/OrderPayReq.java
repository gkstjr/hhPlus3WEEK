package hhplus.ecommerce.interfaces.order;

import hhplus.ecommerce.application.order.OrderPayCriteria;
import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.user.User;

import java.util.List;

public record OrderPayReq(
        List<OrderPayDto> orderItems,
        long issuedCouponId
) {

    public OrderPayCriteria toCriteria(User user) {
        return new OrderPayCriteria(user,orderItems, issuedCouponId);
    }
}
