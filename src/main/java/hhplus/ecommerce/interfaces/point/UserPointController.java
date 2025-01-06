package hhplus.ecommerce.interfaces.point;

import hhplus.ecommerce.domain.point.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Tag(name = "포인트 충전 / 조회")
public class UserPointController {

    PointService pointService;
    public UserPointController(PointService pointService) {
        this.pointService = pointService;
    }
    /*
     *  유저 인증/인가 관련 기능 없으므로 사용자가 필요한 API는 쿼리파라미터로 사용자 ID 받아서 진행
     * */
    @Operation(summary = "잔액 조회", description = "사용자의 잔액을 조회합니다.")
    @GetMapping("/{userId}/points")
    public ResponseEntity<Map<String, Object>> getBalance(@PathVariable long userId) {

        pointService.getUserPoint(userId);

        return ResponseEntity.ok(new HashMap<>());
    }

    @Operation(summary = "잔액 충전", description = "사용자의 잔액을 충전합니다.")
    @PostMapping("/users/{userId}/balance")
    public ResponseEntity<String> rechargeBalance(@PathVariable Long userId, @RequestBody Map<String, Long> request) {
        Long amount = request.get("amount");
        return ResponseEntity.ok("잔액 충전 포인트 : " + amount);
    }
}
