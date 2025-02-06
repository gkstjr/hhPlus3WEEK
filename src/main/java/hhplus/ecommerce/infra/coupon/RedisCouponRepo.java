package hhplus.ecommerce.infra.coupon;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class RedisCouponRepo {
    private static final String couponRequestKeyName = "issueCoupon:";
    private static final String couponInfo = "couponInfo:";

    private final RedisTemplate<String,String> redisTemplate;


    public Long getValue(long couponId) {
        String value = redisTemplate.opsForValue().get(couponInfo + couponId);
        return value != null ? Long.parseLong(value) : null;
    }

    public void saveIssuableCount(long couponId, Long issuableCount) {
        redisTemplate.opsForValue().set(couponInfo + couponId, String.valueOf(issuableCount));
    }
    public void deleteIssuableCoupon(long couponId) {
        redisTemplate.delete(couponInfo + couponId);
    }
    public void saveCouponRequest(long couponId, Long userId) {
        redisTemplate.opsForZSet().add(couponRequestKeyName + couponId , String.valueOf(userId),System.currentTimeMillis());
    }

    public Set<String> getCouponRequestKeys() {
        return redisTemplate.keys(couponRequestKeyName + "*");
    }

    public Set<String> getCouponRequestUserIds(String couponKey, int size) {
        ZSetOperations<String,String> zSetOps = redisTemplate.opsForZSet();
        return zSetOps.range(couponKey,0, size);
    }
    public void deleteCouponRequest(long couponId , int size) {
        redisTemplate.opsForZSet().removeRange(couponRequestKeyName + couponId , 0 , size - 1);
    }
    public void deleteCouponRequestKey(String couponKey) {
        redisTemplate.delete(couponKey);
    }
    public void saveSuccessIssue(List<Long> successUserIds, long couponId) {
        List<String> formattedValue = successUserIds.stream()
                .map(userId -> couponId + "::" + userId) // "couponId::userId" 형식으로 변환 -> 알림스케쥴러에서의 redis 조회 성능을 향상시키기 위해 key 한개 저장
                .toList();

        redisTemplate.opsForList().leftPushAll("success-issue", formattedValue);
    }

    public void saveFailIssue(List<Long> failUserIds, long couponId) {
        List<String> formattedValue = failUserIds.stream()
                .map(userId -> couponId + "::" + userId) // "couponId::userId" 형식으로 변환
                .toList();

        redisTemplate.opsForList().leftPushAll("fail-issue", formattedValue);
    }

}
