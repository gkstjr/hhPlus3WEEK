package hhplus.ecommerce.domain.payment;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.order.IOrderRepository;
import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.payment.dto.PayCommand;
import hhplus.ecommerce.domain.payment.dto.PayInfo;
import hhplus.ecommerce.domain.payment.model.Payment;
import hhplus.ecommerce.domain.point.IPointRepository;
import hhplus.ecommerce.domain.point.model.Point;
import hhplus.ecommerce.domain.user.IUserRepository;
import hhplus.ecommerce.domain.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentService {
    private final IOrderRepository iOrderRepository;
    private final IPointRepository iPointRepository;
    private final IPaymentRepository iPaymentRepository;
    public PaymentService(IOrderRepository iOrderRepository, IPointRepository iPointRepository, IPaymentRepository iPaymentRepository) {
        this.iOrderRepository = iOrderRepository;
        this.iPointRepository = iPointRepository;
        this.iPaymentRepository = iPaymentRepository;
    }

    @Transactional
    public PayInfo pay(PayCommand payCommand) {
        Order getOrder = iOrderRepository.findById(payCommand.orderId()).orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
        Point getPoint =  iPointRepository.findByUserIdWithLock(payCommand.userId()).orElseThrow(()-> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        Payment payment = iPaymentRepository.save(Payment.createPay(getOrder , getPoint));

        return PayInfo.from(payment);
    }
}
