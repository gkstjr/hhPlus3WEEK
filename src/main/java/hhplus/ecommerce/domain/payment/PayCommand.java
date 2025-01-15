package hhplus.ecommerce.domain.payment;

public record PayCommand(
        long orderId,
        long userId

) {
}
