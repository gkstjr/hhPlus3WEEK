package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class CouponRequestProcessor {
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private static final int BATCH_SIZE = 500;
    private static final String couponRequestKeyName = "issueCoupon:";
    @Transactional
    public void issueCouponRequest() {
        Set<String> couponKeys = couponRepository.getCouponRequestKeys();

        if(couponKeys == null || couponKeys.isEmpty()) return;

        for(String couponKey : couponKeys) {
            processIssueCoupon(couponKey);
        }
    }

    private void processIssueCoupon(String couponKey) {
        List<Long> successUserIds = new ArrayList<>();
        List<Long> failedUserIds = new ArrayList<>();
        Set<String> userIds = couponRepository.getCouponRequestUserIds(couponKey,BATCH_SIZE);

        if(userIds == null || userIds.isEmpty()) {
            couponRepository.deleteCouponRequestKey(couponKey);
            return;
        }

        long couponId = Long.parseLong(couponKey.replace(couponRequestKeyName, ""));
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new BusinessException(ErrorCode.COUPON_NOT_FOUND));

        long issuableCount = coupon.calculateIssuableCount();
        for(String userId : userIds) {
            int issueCount = 0;
            if(issuableCount <= issueCount) {
                couponRepository.deleteIssuableCoupon(couponId);
                break;
            }
            try{
                User user = userRepository.findById(Long.parseLong(userId)).orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
                IssuedCoupon issuedCoupon = coupon.issue(user);
                couponRepository.saveIssuedCoupon(issuedCoupon);

                issueCount++;
                successUserIds.add(Long.valueOf(userId));
            }catch(Exception e) {
                failedUserIds.add(Long.valueOf(userId));
            }
        }

        couponRepository.deleteCouponRequest(couponId,userIds.size()); //발급요청처리 개수만큼 pop

        //성공,실패 저장
        if(!successUserIds.isEmpty()) couponRepository.saveSuccessIssue(successUserIds,couponId);
        if(!failedUserIds.isEmpty()) couponRepository.saveFailIssue(failedUserIds,couponId);

    }
}
