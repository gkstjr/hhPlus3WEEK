package hhplus.ecommerce.coupon.domain.dto;

import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.coupon.domain.model.Coupon;

import java.time.LocalDate;

public record IssueCouponInfo(
        long issueCouponId,
        String couponName,
        long discountPrice,
        int maxIssuedCount,
        int issuedCount,
        LocalDate validUntil) {
    public static IssueCouponInfo from(IssuedCoupon issuedCoupon) {
        return new IssueCouponInfo(
                issuedCoupon.getId(),
                issuedCoupon.getCoupon().getName(),
                issuedCoupon.getCoupon().getDiscountPrice(),
                issuedCoupon.getCoupon().getMaxIssuedCount(),
                issuedCoupon.getCoupon().getIssuedCount(),
                issuedCoupon.getCoupon().getValidUntil()
        );
    }
}
