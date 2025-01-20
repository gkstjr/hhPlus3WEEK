package hhplus.ecommerce.interfaces.order;

import hhplus.ecommerce.application.order.OrderPayCriteria;
import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.user.User;

import java.util.List;

public record OrderPayReq(
        User user,
        List<OrderPayDto> orderItems,
        long issuedCouponId
) {

    public OrderPayCriteria toCriteria() {
        return new OrderPayCriteria(user, orderItems, issuedCouponId);
    }
}
