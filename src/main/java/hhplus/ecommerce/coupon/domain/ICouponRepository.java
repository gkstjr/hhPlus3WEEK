package hhplus.ecommerce.coupon.domain;

import hhplus.ecommerce.coupon.domain.model.Coupon;

import java.util.Optional;

public interface ICouponRepository {
    Optional<Coupon> findByIdWithLock(long couponId);

    Coupon save(Coupon getCoupon);

    void deleteAll();
}
