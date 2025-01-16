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
    public PayInfo pay(PayCommand payCommand) {
        Order getOrder = orderRepository.findById(payCommand.orderId()).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        Point getPoint =  pointRepository.findByUserIdWithLock(payCommand.user().getId()).orElseThrow(()-> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        Payment payment = paymentRepository.save(Payment.createPay(getOrder , getPoint));

        return PayInfo.from(payment);
    }
}
