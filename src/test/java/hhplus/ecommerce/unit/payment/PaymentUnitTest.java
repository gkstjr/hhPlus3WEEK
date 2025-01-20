package hhplus.ecommerce.unit.payment;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.payment.Payment;
import hhplus.ecommerce.domain.point.Point;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class PaymentUnitTest {

    @Test
    public void 결제성공() {
        //given
        Order order = Order.builder()
                .totalAmount(20000)
                .build();
        User user = User.builder()
                .name("기만석")
                .build();
        long discountPrice = 5000; //쿠폰 할인금액

        //when
        Payment result = Payment.createPay(order,user , 5000);

        //then
        assertThat(result.getAmount()).isEqualTo(order.getTotalAmount() - discountPrice);
    }
}
