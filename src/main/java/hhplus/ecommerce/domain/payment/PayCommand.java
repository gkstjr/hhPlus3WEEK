package hhplus.ecommerce.domain.payment;

import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.user.User;

public record PayCommand(
        Order order,
        User user,

        long discountAmount

) {
}
