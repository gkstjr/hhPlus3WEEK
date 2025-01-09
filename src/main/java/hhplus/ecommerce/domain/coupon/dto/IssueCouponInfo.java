package hhplus.ecommerce.domain.coupon.dto;

import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.model.Coupon;

public record IssueCouponInfo(
        long issueCouponId,
        Coupon coupon
) {
    public static IssueCouponInfo from(IssuedCoupon issuedCoupon) {
        return new IssueCouponInfo(issuedCoupon.getId(),issuedCoupon.getCoupon());
    }
}
