package hhplus.ecommerce.domain.coupon.dto;

public record IssueCouponCommand(
        long userId,
        long couponId
) {
}
