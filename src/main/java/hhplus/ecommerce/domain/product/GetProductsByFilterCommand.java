package hhplus.ecommerce.domain.product;

public record GetProductsByFilterCommand(
        String productName,
        Long minPrice,
        Long maxPrice


) {
}
