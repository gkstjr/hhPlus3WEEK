package hhplus.ecommerce.interfaces.product;

import hhplus.ecommerce.domain.product.GetProductsByFilterInfo;

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
