package hhplus.ecommerce.unit.payment;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.order.IOrderRepository;
import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.dto.PayCommand;
import hhplus.ecommerce.domain.point.IPointRepository;
import org.aspectj.weaver.ast.Or;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentServiceUnitTest {

    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private IOrderRepository iOrderRepository;
    @Mock
    private IPointRepository iPointRepository;

    @Test
    public void 결제시_유효하지않은주문ID_ORDER_NOT_FOUND() {
        //given
        long orderId = 1L;
        long userId = 1L;
        PayCommand payCommand = new PayCommand(orderId,userId);

        when(iOrderRepository.findById(orderId)).thenReturn(Optional.empty());

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
        PayCommand payCommand = new PayCommand(orderId,userId);

        when(iOrderRepository.findById(orderId)).thenReturn(Optional.of(new Order()));
        when(iPointRepository.findByUserIdWithLock(userId)).thenReturn(Optional.empty());

        //when,then
        assertThatThrownBy(()-> paymentService.pay(payCommand))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.POINT_NOT_FOUND);
    }
}
