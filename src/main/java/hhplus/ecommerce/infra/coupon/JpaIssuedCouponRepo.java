package hhplus.ecommerce.infra.coupon;

import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaIssuedCouponRepo extends JpaRepository<IssuedCoupon,Long> {
    @Query("select ic from IssuedCoupon ic join fetch ic.coupon where ic.id = :issuedCouponId and ic.user.id = :userId")
    Optional<IssuedCoupon> findByIdWithCoupon(Long issuedCouponId , Long userId);

    List<IssuedCoupon> findAllByUserId(long userId);
}
