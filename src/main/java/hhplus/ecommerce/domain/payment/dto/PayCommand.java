package hhplus.ecommerce.domain.payment.dto;

public record PayCommand(
        long orderId,
        long userId

) {
}
