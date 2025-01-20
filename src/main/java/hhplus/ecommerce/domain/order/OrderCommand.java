package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.user.User;

import java.util.List;

public record OrderCommand(
        User user,
        List<OrderProduct> orderProducts
) {

}
