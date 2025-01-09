package hhplus.ecommerce.domain.order.dto;

public record OrderItemDto(
        long productId,
        int quantity
) {
}
