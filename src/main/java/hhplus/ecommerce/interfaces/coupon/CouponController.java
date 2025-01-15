package hhplus.ecommerce.interfaces.coupon;

import hhplus.ecommerce.domain.coupon.CouponService;
import hhplus.ecommerce.domain.coupon.IssueCouponCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class CouponController {
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @Operation(summary = "선착순 쿠폰 발급", description = "사용자에게 선착순 쿠폰을 발급합니다.")
    @PostMapping("/{userId}/coupons/{couponId}")
    public ResponseEntity<IssueCouponResp> issueCoupon(@PathVariable long userId , @PathVariable long couponId) {

        return ResponseEntity.ok(IssueCouponResp.from(couponService.issueCoupon(new IssueCouponCommand(userId,couponId))));
    }

    @Operation(summary = "보유 쿠폰 조회", description = "사용자가 보유한 쿠폰 목록을 조회합니다.")
    @GetMapping("/{userId}/coupons")
    public ResponseEntity<List<IssueCouponResp>> getUserCoupons(@PathVariable Long userId) {

        return ResponseEntity.ok(couponService.getIssueCoupon(userId).stream().map(IssueCouponResp::from).toList());
    }
}
