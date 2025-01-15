package hhplus.ecommerce.domain.payment;

import hhplus.ecommerce.domain.user.User;

public record PayCommand(
        long orderId,
        User user

) {
}
