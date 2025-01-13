package hhplus.ecommerce.coupon.domain.dto;

public record IssueCouponCommand(
        long userId,
        long couponId
) {
}
