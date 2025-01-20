package hhplus.ecommerce.unit.point;

import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.payment.Payment;
import hhplus.ecommerce.domain.point.Point;
import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PointUnitTest {
    @Test
    public void 포인트시용시_보유포인트를초과하면_OUT_OF_POINT() {
        //given
        User user = User.builder()
                .name("기만석")
                .build();
        Point point = Point.builder()
                .point(19999)
                .user(user)
                .build();
        long totalAmount = 20000;

        //when,then
        assertThatThrownBy(() -> point.subtractPoint(totalAmount))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode" , ErrorCode.OUT_OF_POINT);
    }
}
