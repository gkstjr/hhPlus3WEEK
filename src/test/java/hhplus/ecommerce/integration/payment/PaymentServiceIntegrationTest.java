package hhplus.ecommerce.integration.payment;

import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.payment.PaymentRepository;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.payment.PayCommand;
import hhplus.ecommerce.domain.payment.PayInfo;
import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.point.Point;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static hhplus.ecommerce.domain.order.Order.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PaymentServiceIntegrationTest {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private EntityManager entityManager;
    @BeforeEach
    void before() {
        pointRepository.deleteAll();
        paymentRepository.deleteAll();
        orderRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void 결제성공() {
        //given
        Order order = orderRepository.save(
                 builder()
                .totalAmount(19999)
                .build());
        User user = User.builder()
                .name("기만석")
                .build();
        userRepository.save(user);

        //when
        PayInfo result = paymentService.pay(new PayCommand(order,user,0));

        //then
        assertThat(result.totalAmount()).isEqualTo(order.getTotalAmount());
        assertThat(result.orderStatus()).isEqualTo(OrderStatus.PAYMENT_COMPLETED);
    }
}
