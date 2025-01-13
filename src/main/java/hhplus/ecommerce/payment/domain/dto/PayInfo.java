package hhplus.ecommerce.payment.domain.dto;

import hhplus.ecommerce.order.domain.model.OrderStatus;
import hhplus.ecommerce.payment.domain.model.Payment;

public record PayInfo(
        long remindPoint,
        OrderStatus orderStatus
) {
    public static PayInfo from(Payment payment) {

        return new PayInfo(payment.getUser().getPoint().getPoint() , payment.getOrder().getStatus());
    }
}
