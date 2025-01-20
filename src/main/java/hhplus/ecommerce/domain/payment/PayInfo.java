package hhplus.ecommerce.domain.payment;

import static hhplus.ecommerce.domain.order.Order.*;

public record PayInfo(
        long totalAmount,
        OrderStatus orderStatus
) {
    public static PayInfo from(Payment payment) {

        return new PayInfo(payment.getAmount() , payment.getOrder().getStatus());
    }
}
