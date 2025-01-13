package hhplus.ecommerce.payment.application;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.order.domain.IOrderRepository;
import hhplus.ecommerce.order.domain.model.Order;
import hhplus.ecommerce.payment.domain.IPaymentRepository;
import hhplus.ecommerce.payment.domain.dto.PayCommand;
import hhplus.ecommerce.payment.domain.dto.PayInfo;
import hhplus.ecommerce.payment.domain.model.Payment;
import hhplus.ecommerce.point.domain.IPointRepository;
import hhplus.ecommerce.point.domain.model.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
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
