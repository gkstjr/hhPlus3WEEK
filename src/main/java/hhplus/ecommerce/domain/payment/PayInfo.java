package hhplus.ecommerce.domain.payment;

import static hhplus.ecommerce.domain.order.Order.*;

public record PayInfo(
        long remindPoint,
        OrderStatus orderStatus
) {
    public static PayInfo from(Payment payment) {

        return new PayInfo(payment.getUser().getPoint().getPoint() , payment.getOrder().getStatus());
    }
}
