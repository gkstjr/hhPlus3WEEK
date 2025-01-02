package hhplus.ecommerce.interfaces.point;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/points")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class PointController {
    @Operation(summary = "잔액 조회", description = "사용자의 잔액을 조회합니다.")
    @GetMapping("/users/{userId}/balance")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable Long userId) {
        Map<String, Object> response = new HashMap<>();
        response.put("userId", userId);
        response.put("balance", 1000L);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "잔액 충전", description = "사용자의 잔액을 충전합니다.")
    @PostMapping("/users/{userId}/balance")
    public ResponseEntity<String> rechargeBalance(@PathVariable Long userId, @RequestBody Map<String, Long> request) {
        Long amount = request.get("amount");
        return ResponseEntity.ok("잔액 충전 포인트 : " + amount);
    }
}
