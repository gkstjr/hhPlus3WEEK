package hhplus.ecommerce.product.interfaces.dto;

import hhplus.ecommerce.product.domain.dto.GetProductsByFilterCommand;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public record GetProductsReq(
        String productName,
        long minPrice,
        long maxPrice,
        int page,
        int size
) {
    public GetProductsReq {
        if (page < 0) page = 0;
        if (size <= 0) size = 10;
    }

    public GetProductsByFilterCommand toCommand() {
        return new GetProductsByFilterCommand(productName, minPrice, maxPrice);
    }

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
