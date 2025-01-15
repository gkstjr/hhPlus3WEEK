package hhplus.ecommerce.domain.order;

public record OrderItemDto(
        long productId,
        int quantity
) {
}
