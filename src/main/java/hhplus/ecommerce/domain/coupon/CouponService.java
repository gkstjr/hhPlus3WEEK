package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.dto.IssueCouponCommand;
import hhplus.ecommerce.domain.coupon.dto.IssueCouponInfo;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.model.Coupon;
import hhplus.ecommerce.domain.user.IUserRepository;
import hhplus.ecommerce.domain.user.model.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponService {
    private final ICouponRepository iCouponRepository;
    private final IUserRepository iUserRepository;
    private final IIssuedCouponRepository iIssuedCouponRepository;
    public CouponService(ICouponRepository iCouponRepository, IUserRepository iUserRepository, IIssuedCouponRepository iIssuedCouponRepository) {
        this.iCouponRepository = iCouponRepository;
        this.iUserRepository = iUserRepository;
        this.iIssuedCouponRepository = iIssuedCouponRepository;
    }
    @Transactional
    public IssueCouponInfo issueCoupon(IssueCouponCommand issueCouponCommand) {
        Coupon getCoupon = iCouponRepository.findByIdWithLock(issueCouponCommand.couponId()).orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND));
        User getUser =  iUserRepository.findById(issueCouponCommand.userId()).orElseThrow(()-> new BusinessException(ErrorCode.USER_NOT_FOUND));

        IssuedCoupon issuedCoupon = IssuedCoupon.issue(getCoupon, getUser);

        try{
            issuedCoupon = iIssuedCouponRepository.save(issuedCoupon);
        }catch (DataIntegrityViolationException ex) {
            throw new BusinessException(ErrorCode.ALREADY_ISSUE_COUPON);
        }

        return IssueCouponInfo.from(issuedCoupon);
    }

    public List<IssueCouponInfo> getIssueCoupon(long userId) {
        List<IssuedCoupon> issuedCoupon = iIssuedCouponRepository.findAllByUserId(userId);

        List<IssueCouponInfo> result = issuedCoupon.stream()
                .map(IssueCouponInfo::from)
                .collect(Collectors.toList());

        return result;
    }
}
