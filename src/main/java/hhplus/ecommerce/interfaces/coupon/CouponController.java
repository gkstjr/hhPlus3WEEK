package hhplus.ecommerce.interfaces.coupon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/coupons")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class CouponController {

    @Operation(summary = "선착순 쿠폰 발급", description = "사용자에게 선착순 쿠폰을 발급합니다.")
    @PostMapping("/coupons")
    public ResponseEntity<String> issueCoupon(@RequestBody Map<String, Long> request) {
        Long userId = request.get("userId");
        Long couponId = request.get("couponId");
        return ResponseEntity.ok("쿠폰 발급 성공 / 사용자 id : " + userId + " , 쿠폰ID: " + couponId);
    }

    @Operation(summary = "보유 쿠폰 조회", description = "사용자가 보유한 쿠폰 목록을 조회합니다.")
    @GetMapping("/users/{userId}/coupons")
    public ResponseEntity<List<Map<String, Object>>> getUserCoupons(@PathVariable Long userId) {
        List<Map<String, Object>> coupons = new ArrayList<>();
        Map<String, Object> coupon = new HashMap<>();
        coupon.put("id", 1L);
        coupon.put("name", "할인쿠폰");
        coupon.put("discountAmount", 5000L);
        coupons.add(coupon);
        return ResponseEntity.ok(coupons);
    }
}
