package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.domain.order.OrderProduct;

import java.util.List;

public record SubtractStockCommand(

        List<OrderProduct> orderProducts
) {
}
