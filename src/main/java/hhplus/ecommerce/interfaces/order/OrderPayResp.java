package hhplus.ecommerce.interfaces.order;

import hhplus.ecommerce.application.order.OrderPayResult;
import hhplus.ecommerce.application.order.OrderProductDto;

import java.util.List;

import static hhplus.ecommerce.domain.order.Order.*;

public record OrderPayResp(
        long orderId,
        long userId,
        long totalAmount,
        long discountAmount,
        long remindPoint,
        OrderStatus orderStatus,
        List<OrderProductDto> orderProducts
) {
    public static OrderPayResp from(OrderPayResult orderPayResult) {

        return new OrderPayResp(
                orderPayResult.orderId(),
                orderPayResult.userId(),
                orderPayResult.totalAmount(),
                orderPayResult.discountAmount(),
                orderPayResult.remindPoint(),
                orderPayResult.orderStatus(),
                orderPayResult.orderProducts()
        );
    }
}
