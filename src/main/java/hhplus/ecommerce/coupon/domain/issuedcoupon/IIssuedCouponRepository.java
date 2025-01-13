package hhplus.ecommerce.coupon.domain.issuedcoupon;

import java.util.List;
import java.util.Optional;

public interface IIssuedCouponRepository {
    IssuedCoupon save(IssuedCoupon issuedCoupon);

    void deleteAll();

    Optional<IssuedCoupon> findByIdWithCoupon(Long issuedCouponId);

    List<IssuedCoupon> findAllByUserId(long userId);
}
