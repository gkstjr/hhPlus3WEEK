package hhplus.ecommerce.unit.payment;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.order.domain.model.Order;
import hhplus.ecommerce.payment.domain.model.Payment;
import hhplus.ecommerce.point.domain.model.Point;
import hhplus.ecommerce.user.domain.model.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PaymentUnitTest {

    @Test
    public void 결제시_결제금액이_보유포인트를초과하면_OUT_OF_POINT() {
        //given
        Order order = Order.builder()
                .totalAmount(20000)
                .build();
        User user = User.builder()
                .name("기만석")
                .build();
        Point point = Point.builder()
                .point(19999)
                .user(user)
                .build();
        //when,then
        assertThatThrownBy(() -> Payment.createPay(order,point))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode" , ErrorCode.OUT_OF_POINT);
    }

    @Test
    public void 결제성공() {
        //given
        Order order = Order.builder()
                .totalAmount(20000)
                .build();
        User user = User.builder()
                .name("기만석")
                .build();
        Point point = Point.builder()
                .point(20005)
                .user(user)
                .build();
        //when
        Payment result = Payment.createPay(order,point);

        //then
        assertThat(result.getUser().getPoint().getPoint()).isEqualTo(5);
    }
}
