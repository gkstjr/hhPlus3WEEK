package hhplus.ecommerce.product.domain.dto;

public record GetProductsByFilterCommand(
        String productName,
        Long minPrice,
        Long maxPrice


) {
}
