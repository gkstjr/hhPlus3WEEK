package hhplus.ecommerce.domain.order;

public record OrderPayDto(
        long productId,
        int quantity
) {
}
