package hhplus.ecommerce.infra.coupon;

import hhplus.ecommerce.domain.coupon.model.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface JpaCouponRepository extends JpaRepository<Coupon,Long> {
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from Coupon c where c.id = :couponId")
    Optional<Coupon> findByIdWithLock(Long couponId);
}
