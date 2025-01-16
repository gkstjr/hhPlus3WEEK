package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.application.order.OrderProductDto;
import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderDetailDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.user.User;

import java.util.List;

public record OrderProductsInfo(
    List<OrderProduct> orderProducts
) {

    public OrderCommand toOrderCommand(User user) {
        return new OrderCommand(user , orderProducts);
    }
}
