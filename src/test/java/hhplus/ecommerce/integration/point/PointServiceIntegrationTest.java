package hhplus.ecommerce.integration.point;

import hhplus.ecommerce.point.domain.IPointRepository;
import hhplus.ecommerce.point.application.PointService;
import hhplus.ecommerce.point.domain.dto.ChargePointCommand;
import hhplus.ecommerce.point.domain.dto.UserPointInfo;
import hhplus.ecommerce.point.domain.model.Point;
import hhplus.ecommerce.user.domain.IUserRepository;
import hhplus.ecommerce.user.domain.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PointServiceIntegrationTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IPointRepository iPointRepository;

    @Test
    public void 포인트조회() {
        //given
        String name = "기만석";
        long currentPoint = 5000;
        User user = iUserRepository.save(getUser(name));
        Point point =iPointRepository.save(getPoint(user, currentPoint));

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
        User user = iUserRepository.save(getUser(name));
        Point point =iPointRepository.save(getPoint(user, currentPoint));
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
