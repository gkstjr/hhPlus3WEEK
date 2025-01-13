package hhplus.ecommerce.integration.payment;

import hhplus.ecommerce.order.domain.IOrderRepository;
import hhplus.ecommerce.order.domain.model.Order;
import hhplus.ecommerce.order.domain.model.OrderStatus;
import hhplus.ecommerce.payment.domain.IPaymentRepository;
import hhplus.ecommerce.payment.application.PaymentService;
import hhplus.ecommerce.payment.domain.dto.PayCommand;
import hhplus.ecommerce.payment.domain.dto.PayInfo;
import hhplus.ecommerce.point.domain.IPointRepository;
import hhplus.ecommerce.point.domain.model.Point;
import hhplus.ecommerce.user.domain.IUserRepository;
import hhplus.ecommerce.user.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IOrderRepository iOrderRepository;

    @Autowired
    private IPointRepository iPointRepository;
    @Autowired
    private IPaymentRepository iPaymentRepository;
    @BeforeEach
    void before() {
        iUserRepository.deleteAll();
        iPointRepository.deleteAll();
        iPaymentRepository.deleteAll();
        iOrderRepository.deleteAll();
    }
    @Test
    public void 결제성공() {
        //given
        Order order = iOrderRepository.save(
                 Order.builder()
                .totalAmount(19999)
                .build());
        User user = User.builder()
                .name("기만석")
                .build();
        iUserRepository.save(user);

        Point point = iPointRepository.save(
                 Point.builder()
                .point(20000)
                .user(user)
                .build());
        //when
        PayInfo result = paymentService.pay(new PayCommand(order.getId(),point.getUser().getId()));

        //then
        assertThat(result.remindPoint()).isEqualTo(1);
        assertThat(result.orderStatus()).isEqualTo(OrderStatus.PAYMENT_COMPLETED);
    }
}
