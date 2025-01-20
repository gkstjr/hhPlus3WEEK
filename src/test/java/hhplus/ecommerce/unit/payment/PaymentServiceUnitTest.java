package hhplus.ecommerce.unit.payment;

import hhplus.ecommerce.domain.payment.*;
import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.point.PointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceUnitTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentRepository paymentRepository;


    @Test
    public void 결제성공() {
        //given
        long orderId = 1L;
        long userId = 1L;
        long discountAmount = 5000; //쿠폰 할인금액
        User user = User.builder().id(userId).build();
        Order order = Order.builder().id(orderId).totalAmount(20000).build();
        PayCommand payCommand = new PayCommand(order,user,discountAmount);

        when(paymentRepository.save(any(Payment.class))).thenReturn(Payment.createPay(order,user,discountAmount));

        //when
        PayInfo result = paymentService.pay(payCommand);
        //then
        assertThat(result.totalAmount()).isEqualTo(order.getTotalAmount() - discountAmount);
        assertThat(result.orderStatus()).isEqualTo(Order.OrderStatus.PAYMENT_COMPLETED);
    }
}
