package hhplus.ecommerce.domain.product.dto;

public record PopularProductDto(
        Long productId,
        String productName,
        Long totalQuantity) {
}
