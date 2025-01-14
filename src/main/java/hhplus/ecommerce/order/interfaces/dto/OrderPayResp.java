package hhplus.ecommerce.order.interfaces.dto;

import hhplus.ecommerce.order.application.dto.OrderPayResult;
import hhplus.ecommerce.order.application.dto.OrderProductDto;
import hhplus.ecommerce.order.domain.model.OrderStatus;

import java.util.List;

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
