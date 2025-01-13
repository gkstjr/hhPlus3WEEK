package hhplus.ecommerce.payment.domain.dto;

public record PayCommand(
        long orderId,
        long userId

) {
}
