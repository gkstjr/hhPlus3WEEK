package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.payment.PayCommand;
import hhplus.ecommerce.domain.product.OrderProductsCommand;
import hhplus.ecommerce.domain.user.User;

import java.util.List;

public record OrderPayCriteria(
        User user ,
        List<OrderPayDto> orderItems,

        Long issuedCouponId
) {

    public OrderProductsCommand toGetStocksWithProductCommand(){
      return new OrderProductsCommand(orderItems);
    }

}
