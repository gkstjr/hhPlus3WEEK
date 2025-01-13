package hhplus.ecommerce.product.domain.dto;

public record PopularProductDto(
        Long productId,
        String productName,
        Long totalQuantity) {
}
