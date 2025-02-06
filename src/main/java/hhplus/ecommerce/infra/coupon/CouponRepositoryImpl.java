package hhplus.ecommerce.infra.coupon;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryImpl implements CouponRepository {

    private final JpaCouponRepository jpaCouponRepository;
    private final JpaIssuedCouponRepo jpaIssuedCouponRepo;
    private final RedisCouponRepo redisCouponRepo;

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

    //Redis
    @Override
    public Long findIssuableCountByCouponId(long couponId) {
        return redisCouponRepo.getValue(couponId);
    }

    @Override
    public void saveIssuableCount(long couponId, Long issuableCount) {
        redisCouponRepo.saveIssuableCount(couponId , issuableCount);
    }

    @Override
    public void saveCouponRequest(long couponId, Long userId) {
        redisCouponRepo.saveCouponRequest(couponId , userId);
    }

    @Override
    public Set<String> getCouponRequestKeys() {
        return redisCouponRepo.getCouponRequestKeys();
    }

    @Override
    public Set<String> getCouponRequestUserIds(String couponKey, int size) {
        return redisCouponRepo.getCouponRequestUserIds(couponKey , size);
    }

    @Override
    public void deleteIssuableCoupon(long couponId) {
        redisCouponRepo.deleteIssuableCoupon(couponId);
    }

    @Override
    public void saveSuccessIssue(List<Long> successUserIds, long couponId) {
        redisCouponRepo.saveSuccessIssue(successUserIds ,couponId);
    }

    @Override
    public void saveFailIssue(List<Long> failUserIds, long couponId) {
        redisCouponRepo.saveFailIssue(failUserIds,couponId);
    }

    @Override
    public void deleteCouponRequest(long couponId , int size) {
        redisCouponRepo.deleteCouponRequest(couponId , size);
    }

    @Override
    public void deleteCouponRequestKey(String couponKey) {
        redisCouponRepo.deleteCouponRequestKey(couponKey);
    }
}
