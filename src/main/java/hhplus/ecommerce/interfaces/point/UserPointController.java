package hhplus.ecommerce.interfaces.point;

import hhplus.ecommerce.domain.point.PointService;
import hhplus.ecommerce.domain.point.ChargePointCommand;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<UserPointResp> getBalance(@PathVariable long userId) {
        return ResponseEntity.ok(UserPointResp.from(pointService.getUserPoint(userId)));
    }

    @Operation(summary = "잔액 충전", description = "사용자의 잔액을 충전합니다.")
    @PostMapping("/{userId}/points")
    public ResponseEntity<UserPointResp> rechargeBalance(@PathVariable Long userId, @RequestBody long chargePoint) {
        return ResponseEntity.ok(UserPointResp.from(pointService.chargePoint(new ChargePointCommand(userId,chargePoint))));
    }
}
