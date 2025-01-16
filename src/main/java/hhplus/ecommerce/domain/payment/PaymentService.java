package hhplus.ecommerce.domain.payment;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.point.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final OrderRepository orderRepository;
    private final PointRepository pointRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public PayInfo pay(PayCommand command) {
        Payment payment = Payment.createPay(command.order() , command.user(),command.discountAmount());

        return PayInfo.from(paymentRepository.save(payment));
    }
}
