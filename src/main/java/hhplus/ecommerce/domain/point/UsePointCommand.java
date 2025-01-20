package hhplus.ecommerce.domain.point;

import hhplus.ecommerce.domain.user.User;

public record UsePointCommand(
        User user,
        long totalAmount
) {
}
