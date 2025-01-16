package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderInfo;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.payment.PayInfo;

import java.util.List;

import static hhplus.ecommerce.domain.order.Order.*;

public record OrderPayResult(
        long orderId,
        long userId ,
        long totalAmount,
        long discountAmount,
        long remindPoint,
        OrderStatus orderStatus,
        List<OrderProductDto> orderProducts

) {
    public static OrderPayResult of(Order order, PayInfo payInfo , long discountAmount , long remindPoint) {
        List<OrderProductDto> orderProductDtos = order.getOrderProductList().stream()
                .map(orderProduct -> new OrderProductDto(
                        orderProduct.getProduct().getId(),
                        orderProduct.getProduct().getName(),
                        orderProduct.getQuantity(),
                        orderProduct.getPrice()
                ))
                .toList();

        return new OrderPayResult(
                order.getId(),
                order.getUser().getId(),
                order.getTotalAmount(),
                discountAmount,
                remindPoint,
                order.getStatus(),
                orderProductDtos
        );    }
}
