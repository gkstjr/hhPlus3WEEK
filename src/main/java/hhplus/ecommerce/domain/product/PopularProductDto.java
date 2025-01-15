package hhplus.ecommerce.domain.product;

public record PopularProductDto(
        Long productId,
        String productName,
        Long totalQuantity) {
}
