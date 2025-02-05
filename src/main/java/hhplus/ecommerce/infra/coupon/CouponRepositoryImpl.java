package hhplus.ecommerce.infra.coupon;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final JpaIssuedCouponRepo jpaIssuedCouponRepo;

    @Override
    public Optional<Coupon> findById(long couponId) {
        return jpaCouponRepository.findById(couponId);
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



    //발급 쿠폰
    @Override
    public IssuedCoupon saveIssuedCoupon(IssuedCoupon issuedCoupon) {
        return jpaIssuedCouponRepo.save(issuedCoupon);
    }

    @Override
    public void deleteAllIssuedCoupon() {
        jpaIssuedCouponRepo.deleteAll();
    }

    @Override
    public Optional<IssuedCoupon> findByIssuedCouponIdWithCoupon(Long issuedCouponId, Long userId) {
        return jpaIssuedCouponRepo.findByIdWithCoupon(issuedCouponId,userId);
    }

    @Override
    public List<IssuedCoupon> findAllIssuedCouponByUserId(long userId) {
        return jpaIssuedCouponRepo.findAllByUserId(userId);
    }
}
