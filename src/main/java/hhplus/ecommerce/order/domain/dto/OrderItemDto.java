package hhplus.ecommerce.order.domain.dto;

public record OrderItemDto(
        long productId,
        int quantity
) {
}
