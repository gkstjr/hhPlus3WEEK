package hhplus.ecommerce.coupon.domain.dto;

import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.coupon.domain.model.Coupon;

public record IssueCouponInfo(
        long issueCouponId,
        Coupon coupon
) {
    public static IssueCouponInfo from(IssuedCoupon issuedCoupon) {
        return new IssueCouponInfo(issuedCoupon.getId(),issuedCoupon.getCoupon());
    }
}
