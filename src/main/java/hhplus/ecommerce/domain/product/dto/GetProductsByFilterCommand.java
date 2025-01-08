package hhplus.ecommerce.domain.product.dto;

public record GetProductsByFilterCommand(
        String productName,
        Long minPrice,
        Long maxPrice


) {
}
