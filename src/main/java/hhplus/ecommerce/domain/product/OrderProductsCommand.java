package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.domain.order.OrderPayDto;

import java.util.List;

public record OrderProductsCommand(
        List<OrderPayDto> orderItems

        ) {
}
