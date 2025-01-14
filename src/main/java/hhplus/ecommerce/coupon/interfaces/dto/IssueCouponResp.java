package hhplus.ecommerce.coupon.interfaces.dto;

import hhplus.ecommerce.coupon.domain.dto.IssueCouponInfo;

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
