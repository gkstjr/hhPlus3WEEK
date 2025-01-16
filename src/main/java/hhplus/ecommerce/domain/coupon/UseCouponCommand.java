package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.domain.user.User;

public record UseCouponCommand(
        Long issuedCouponId,
        User user
) {
}
