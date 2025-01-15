package hhplus.ecommerce.domain.coupon;

public record IssueCouponCommand(
        long userId,
        long couponId
) {
}
