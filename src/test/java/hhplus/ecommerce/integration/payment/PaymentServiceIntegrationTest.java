package hhplus.ecommerce.integration.payment;

import hhplus.ecommerce.domain.order.IOrderRepository;
import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.order.model.OrderStatus;
import hhplus.ecommerce.domain.payment.IPaymentRepository;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.dto.PayCommand;
import hhplus.ecommerce.domain.payment.dto.PayInfo;
import hhplus.ecommerce.domain.point.IPointRepository;
import hhplus.ecommerce.domain.point.model.Point;
import hhplus.ecommerce.domain.user.IUserRepository;
import hhplus.ecommerce.domain.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
