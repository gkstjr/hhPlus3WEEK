package hhplus.ecommerce.infra.coupon;

import hhplus.ecommerce.domain.coupon.model.Coupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaCouponRepository extends JpaRepository<Coupon,Long> {
    @Query("select c from Coupon c where c.id = :couponId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Coupon> findByIdWithLock(Long couponId);
}
