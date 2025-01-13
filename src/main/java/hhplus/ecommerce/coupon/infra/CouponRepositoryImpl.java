package hhplus.ecommerce.coupon.infra;

import hhplus.ecommerce.coupon.domain.ICouponRepository;
import hhplus.ecommerce.coupon.domain.model.Coupon;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CouponRepositoryImpl implements ICouponRepository {

    private final JpaCouponRepository jpaCouponRepository;

    public CouponRepositoryImpl(JpaCouponRepository jpaCouponRepository) {
        this.jpaCouponRepository = jpaCouponRepository;
    }

    @Override
    public Optional<Coupon> findByIdWithLock(long couponId) {
        return jpaCouponRepository.findByIdWithLock(couponId);
    }

    @Override
    public Coupon save(Coupon getCoupon) {
        return jpaCouponRepository.save(getCoupon);
    }

    @Override
    public void deleteAll() {
        jpaCouponRepository.deleteAll();
    }
}
