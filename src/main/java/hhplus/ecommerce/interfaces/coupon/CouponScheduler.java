package hhplus.ecommerce.interfaces.coupon;

import hhplus.ecommerce.domain.coupon.CouponRequestProcessor;
import hhplus.ecommerce.domain.coupon.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponScheduler {

    private final CouponRequestProcessor processor;

    @Scheduled(fixedRate = 5000)
    public void processCoupons() {
        processor.issueCouponRequest();
    }
}
