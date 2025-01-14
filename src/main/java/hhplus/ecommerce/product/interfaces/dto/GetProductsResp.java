package hhplus.ecommerce.product.interfaces.dto;

import hhplus.ecommerce.product.domain.dto.GetProductsByFilterInfo;
import hhplus.ecommerce.product.domain.model.Product;

public record GetProductsResp(
        Long id,
        String name,
        long price,
        int stockQuantity
) {

    public static GetProductsResp from(GetProductsByFilterInfo info) {
        return new GetProductsResp(
                info.id(),
                info.name(),
                info.price(),
                info.stockQuantity()
        );
    }
}
