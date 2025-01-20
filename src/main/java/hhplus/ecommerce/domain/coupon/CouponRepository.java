package hhplus.ecommerce.domain.coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    //쿠폰
    Optional<Coupon> findByIdWithLock(long couponId);

    Coupon save(Coupon getCoupon);

    void deleteAll();


    //발급쿠폰
    IssuedCoupon saveIssuedCoupon(IssuedCoupon issuedCoupon);

    void deleteAllIssuedCoupon();

    Optional<IssuedCoupon> findByIssuedCouponIdWithCoupon(Long issuedCouponId,Long userId);

    List<IssuedCoupon> findAllIssuedCouponByUserId(long userId);
}
