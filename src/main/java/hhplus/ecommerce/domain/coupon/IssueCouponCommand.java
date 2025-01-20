package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.domain.user.User;

public record IssueCouponCommand(
        User user,
        long couponId
) {
}
