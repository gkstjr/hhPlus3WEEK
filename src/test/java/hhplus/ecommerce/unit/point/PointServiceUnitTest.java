package hhplus.ecommerce.unit.point;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.point.IPointRepository;
import hhplus.ecommerce.domain.point.PointService;
import hhplus.ecommerce.domain.point.dto.ChargePointCommand;
import hhplus.ecommerce.domain.point.dto.UserPointInfo;
import hhplus.ecommerce.domain.point.model.Point;
import hhplus.ecommerce.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PointServiceUnitTest {
    @InjectMocks
    private PointService pointService;
    @Mock
    private IPointRepository iPointRepository;
    @Test
    public void 존재하지않는_사용자포인트조회시_PointNotFound반환() {
        //given
        long userId = 1L;
        Mockito.when(iPointRepository.findByUserId(userId)).thenReturn(Optional.empty());
        //when
        //then
        assertThatThrownBy(() -> pointService.getUserPoint(userId))
                        .isInstanceOf(BusinessException.class)
                        .hasMessageContaining(ErrorCode.POINT_NOT_FOUND.getMessage());
    }

    @Test
    public void 포인트조회성공() {
        //given
        long userId = 1L;
        long pointId = 1L;
        long point = 5000;
        User user = getUser(userId , "기만석");
        Point returnPoint = getPoint(pointId, user, point);

        Mockito.when(iPointRepository.findByUserId(userId)).thenReturn(Optional.of(returnPoint));
        //when
        UserPointInfo result = pointService.getUserPoint(userId);

        //then
        assertThat(result.pointId()).isEqualTo(pointId);
        assertThat(result.userId()).isEqualTo(userId);
        assertThat(result.point()).isEqualTo(point);
    }

    @Test
    public void 충전할_금액이_1000포인트미만_또는10_000_000초과시_InvalidPointAmount반환() {
        //given
        long userId = 1L;
        String name = "기만석";
        User user = getUser(userId,name);

        long pointId = 1L;
        long currentPoint = 1000;
        long chargePoint1 = 900;
        long chargePoint2 = 10000001;
        Point point = getPoint(pointId,user,currentPoint);

        Mockito.when(iPointRepository.findByUserId(userId)).thenReturn(Optional.of(point));
        //when
        //then
        assertThatThrownBy(()-> pointService.chargePoint(new ChargePointCommand(userId,chargePoint1)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.INVALID_POINT_AMOUNT.getMessage());

        assertThatThrownBy(()-> pointService.chargePoint(new ChargePointCommand(userId,chargePoint2)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.INVALID_POINT_AMOUNT.getMessage());
    }

    @Test
    public void 충전완료_금액이_포인트보유한도_1억초과시_OVER_POINT_LIMIT반환() {
        //given
        long userId = 1L;
        String name = "기만석";
        User user = getUser(userId,name);

        long pointId = 1L;
        long currentPoint = 90000001;
        long chargePoint = 10000000;
        Point point = getPoint(pointId,user,currentPoint);

        Mockito.when(iPointRepository.findByUserId(userId)).thenReturn(Optional.of(point));
        //when
        //then
        assertThatThrownBy(()-> pointService.chargePoint(new ChargePointCommand(userId,chargePoint)))
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.OVER_POINT_LIMIT.getMessage());
    }

    @Test
    public void 포인트충전성공() {
        //given
        long userId = 1L;
        String name = "기만석";
        User user = getUser(userId,name);

        long pointId = 1L;
        long currentPoint = 50000;
        long chargePoint = 5000;
        Point point = getPoint(pointId,user,currentPoint);

        Mockito.when(iPointRepository.findByUserId(userId)).thenReturn(Optional.of(point));
        //when
        UserPointInfo result = pointService.chargePoint(new ChargePointCommand(userId,chargePoint));

        //then
        assertThat(result.point()).isEqualTo(currentPoint + chargePoint);
        assertThat(result.pointId()).isEqualTo(pointId);
        assertThat(result.userId()).isEqualTo(userId);
    }

    private static Point getPoint(long pointId, User user, long point) {
        Point returnPoint = Point.builder()
                .id(pointId)
                .user(user)
                .point(point)
                .build();
        return returnPoint;
    }

    private static User getUser(long userId , String name) {
        User user = User.builder()
                .id(userId)
                .name(name)
                .build();
        return user;
    }
}
