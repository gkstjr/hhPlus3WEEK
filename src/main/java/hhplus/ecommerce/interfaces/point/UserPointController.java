package hhplus.ecommerce.interfaces.point;

import hhplus.ecommerce.domain.point.PointService;
import hhplus.ecommerce.domain.point.ChargePointCommand;
import hhplus.ecommerce.domain.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "포인트 충전 / 조회")
public class UserPointController {

    private final PointService pointService;

    /*
     *  유저 인증/인가 관련 기능 없으므로 사용자가 필요한 API는 쿼리파라미터로 사용자 ID 받아서 진행
     * */
    @Operation(summary = "잔액 조회", description = "사용자의 잔액을 조회합니다.")
    @GetMapping("/points")
    public ResponseEntity<UserPointResp> getBalance(User user) {
        return ResponseEntity.ok(UserPointResp.from(pointService.getUserPoint(user.getId())));
    }

    @Operation(summary = "잔액 충전", description = "사용자의 잔액을 충전합니다.")
    @PostMapping("/points")
    public ResponseEntity<UserPointResp> rechargeBalance(User user, @RequestBody long chargePoint) {
        return ResponseEntity.ok(UserPointResp.from(pointService.chargePoint(new ChargePointCommand(user.getId(),chargePoint))));
    }

}
