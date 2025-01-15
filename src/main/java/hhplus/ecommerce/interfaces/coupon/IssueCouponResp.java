package hhplus.ecommerce.interfaces.coupon;

import hhplus.ecommerce.domain.coupon.IssueCouponInfo;

import java.time.LocalDate;

public record IssueCouponResp(
        long issuedCouponId,
        String couponName,
        long discountPrice,
        int maxIssuedCount,
        int issuedCount,
        LocalDate validUntil
) {
    public static IssueCouponResp from(IssueCouponInfo info) {
       return new IssueCouponResp(
                info.issueCouponId(),
                info.couponName(),
                info.discountPrice(),
                info.maxIssuedCount(),
                info.issuedCount(),
                info.validUntil()
        );
    }
}
