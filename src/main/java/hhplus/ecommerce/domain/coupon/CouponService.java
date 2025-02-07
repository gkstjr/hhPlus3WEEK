package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.support.lock.DistributedLock;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;
    @Transactional
    public void storeIssuedCouponRequest(IssueCouponCommand command) {
        Long issuableCount = couponRepository.findIssuableCountByCouponId(command.couponId());

        if(issuableCount == null) {
            Coupon coupon = couponRepository.findById(command.couponId()).orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND));
            issuableCount = coupon.calculateIssuableCount();
            couponRepository.saveIssuableCount(command.couponId() , issuableCount);
        }
        if(issuableCount <= 0) throw new BusinessException(ErrorCode.COUPON_MAX_ISSUE);

        couponRepository.saveCouponRequest(command.couponId(),command.user().getId());
    }

    @DistributedLock(key = "#lockName")
    @Transactional
    public IssueCouponInfo issueCoupon(Long lockName,IssueCouponCommand issueCouponCommand) {
        Coupon getCoupon = couponRepository.findByIdWithLock(issueCouponCommand.couponId()).orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND));

        IssuedCoupon issuedCoupon = getCoupon.issue(issueCouponCommand.user());

        try{
            issuedCoupon = couponRepository.saveIssuedCoupon(issuedCoupon);
        }catch (DataIntegrityViolationException ex) {
            throw new BusinessException(ErrorCode.ALREADY_ISSUE_COUPON);
        }

        return IssueCouponInfo.from(issuedCoupon);
    }

    public List<IssueCouponInfo> getIssueCoupon(long userId) {
        List<IssuedCoupon> issuedCoupon = couponRepository.findAllIssuedCouponByUserId(userId);

        List<IssueCouponInfo> result = issuedCoupon.stream()
                .map(IssueCouponInfo::from)
                .collect(Collectors.toList());

        return result;
    }

    public long useCoupon(UseCouponCommand command) {
        if(Objects.isNull(command.issuedCouponId())) return 0;
        IssuedCoupon issuedCoupon = couponRepository.findByIssuedCouponIdWithCoupon(command.issuedCouponId(), command.user().getId()).orElseThrow(() -> new BusinessException(ErrorCode.ISSUEDCOUPON_NOT_FOUND));

        return issuedCoupon.validatedUse();
    }
}
