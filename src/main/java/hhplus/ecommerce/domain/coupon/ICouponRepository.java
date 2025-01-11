package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.domain.coupon.model.Coupon;

import java.util.Optional;

public interface ICouponRepository {
    Optional<Coupon> findByIdWithLock(long couponId);

    Coupon save(Coupon getCoupon);

    void deleteAll();
}
