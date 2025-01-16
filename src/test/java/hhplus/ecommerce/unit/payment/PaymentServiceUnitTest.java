package hhplus.ecommerce.unit.payment;

import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.PayCommand;
import hhplus.ecommerce.domain.point.PointRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceUnitTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private PointRepository pointRepository;

    @Test
    public void 결제시_유효하지않은주문ID_ORDER_NOT_FOUND() {
        //given
        long orderId = 1L;
        long userId = 1L;
        User user = User.builder().id(userId).build();

        PayCommand payCommand = new PayCommand(orderId,user);

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        //when,then
        assertThatThrownBy(()-> paymentService.pay(payCommand))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ORDER_NOT_FOUND);
    }

    @Test
    public void 결제시_유효하지않은포인트ID_POINT_NOT_FOUND() {
        //given
        long orderId = 1L;
        long userId = 1L;
        User user = User.builder().id(userId).build();

        PayCommand payCommand = new PayCommand(orderId,user);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(Order.builder().build()));
        when(pointRepository.findByUserIdWithLock(userId)).thenReturn(Optional.empty());

        //when,then
        assertThatThrownBy(()-> paymentService.pay(payCommand))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.POINT_NOT_FOUND);
    }
}
