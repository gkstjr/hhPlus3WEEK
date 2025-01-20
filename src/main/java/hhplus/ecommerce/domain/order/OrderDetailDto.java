package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.domain.product.Product;

public record OrderDetailDto(
        Product product,
        int quantity
) {

}
