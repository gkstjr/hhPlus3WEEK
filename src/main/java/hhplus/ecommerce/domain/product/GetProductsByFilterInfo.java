package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.interfaces.product.GetProductsResp;

public record GetProductsByFilterInfo(
    Long id,
    String name,
    long price,

    int stockQuantity


) {
    public static GetProductsByFilterInfo from(Product product) {
        return new GetProductsByFilterInfo(
                product.getId(),
                product.getName(),
                product.getPrice() ,
                product.getProductStock() != null ? product.getProductStock().getStock() : 0);
    }

    public GetProductsResp toResp() {
        return new GetProductsResp(id , name , price , stockQuantity);
    }
}

