package hhplus.ecommerce.integration.point;

import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.point.PointService;
import hhplus.ecommerce.domain.point.ChargePointCommand;
import hhplus.ecommerce.domain.point.UserPointInfo;
import hhplus.ecommerce.domain.point.Point;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PointServiceIntegrationTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointRepository pointRepository;

    @Test
    public void 포인트조회() {
        //given
        String name = "기만석";
        long currentPoint = 5000;
        User user = userRepository.save(getUser(name));
        Point point = pointRepository.save(getPoint(user, currentPoint));

        //when
        UserPointInfo result = pointService.getUserPoint(user.getId());
        //then
        assertThat(result.pointId()).isEqualTo(point.getId());
        assertThat(result.userId()).isEqualTo(user.getId());
        assertThat(result.point()).isEqualTo(point.getPoint());
    }
    @Test
    public void 포인트충전() {
        //given
        String name = "기만석";
        long currentPoint = 5000;
        long  chargePoint = 10000;
        User user = userRepository.save(getUser(name));
        Point point = pointRepository.save(getPoint(user, currentPoint));
        //when
        UserPointInfo result = pointService.chargePoint(new ChargePointCommand(user.getId(),chargePoint));
        //then
        assertThat(result.point()).isEqualTo(currentPoint + chargePoint);
        assertThat(result.pointId()).isEqualTo(point.getId());
        assertThat(result.userId()).isEqualTo(user.getId());
    }

    private static Point getPoint(User user, long point) {
        Point returnPoint = Point.builder()
                .user(user)
                .point(point)
                .build();
        return returnPoint;
    }

    private static User getUser(String name) {
        User user = User.builder()
                .name(name)
                .build();
        return user;
    }
}
