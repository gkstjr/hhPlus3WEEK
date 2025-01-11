package hhplus.ecommerce.domain.coupon.issuedcoupon;

import hhplus.ecommerce.domain.coupon.model.Coupon;

import java.util.List;
import java.util.Optional;

public interface IIssuedCouponRepository {
    IssuedCoupon save(IssuedCoupon issuedCoupon);

    void deleteAll();

    Optional<IssuedCoupon> findByIdWithCoupon(Long issuedCouponId);

    List<IssuedCoupon> findAllByUserId(long userId);
}
