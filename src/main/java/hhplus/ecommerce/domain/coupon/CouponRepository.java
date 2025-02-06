package hhplus.ecommerce.domain.coupon;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CouponRepository {
    //쿠폰
    Optional<Coupon> findById(long couponId);

    Optional<Coupon> findByIdWithLock(long couponId);

    Coupon save(Coupon getCoupon);

    void deleteAll();


    //발급쿠폰
    IssuedCoupon saveIssuedCoupon(IssuedCoupon issuedCoupon);

    void deleteAllIssuedCoupon();

    Optional<IssuedCoupon> findByIssuedCouponIdWithCoupon(Long issuedCouponId,Long userId);

    List<IssuedCoupon> findAllIssuedCouponByUserId(long userId);

    //Redis
    Long findIssuableCountByCouponId(long couponId);

    void saveIssuableCount(long couponId, Long issuableCount);

    void saveCouponRequest(long couponId, Long userId);

    public Set<String> getCouponRequestKeys();

    Set<String> getCouponRequestUserIds(String couponKey , int size);

    void deleteIssuableCoupon(long couponId);

    void saveSuccessIssue(List<Long> successUserIds, long couponId);

    void saveFailIssue(List<Long> failUserIds, long couponId);

    void deleteCouponRequest(long couponId, int size);

    void deleteCouponRequestKey(String couponKey);
}
