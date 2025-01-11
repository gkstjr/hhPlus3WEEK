package hhplus.ecommerce.domain.payment.dto;

import hhplus.ecommerce.domain.order.model.OrderStatus;
import hhplus.ecommerce.domain.payment.model.Payment;

public record PayInfo(
        long remindPoint,
        OrderStatus orderStatus
) {
    public static PayInfo from(Payment payment) {

        return new PayInfo(payment.getUser().getPoint().getPoint() , payment.getOrder().getStatus());
    }
}
