package hhplus.ecommerce.application.order;

import hhplus.ecommerce.domain.order.OrderInfo;
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
    public static OrderPayResult of(OrderInfo orderInfo, PayInfo payInfo) {
        List<OrderProductDto> orderProductDtos = orderInfo.orderProducts().stream()
                .map(orderProduct -> new OrderProductDto(
                        orderProduct.getProduct().getId(),
                        orderProduct.getProduct().getName(),
                        orderProduct.getQuantity(),
                        orderProduct.getPrice()
                ))
                .toList();

        return new OrderPayResult(
                orderInfo.orderId(),
                orderInfo.userId(),
                orderInfo.totalAmount(),
                orderInfo.issuedCoupon().getCoupon().getDiscountPrice(),
                payInfo.remindPoint(),
                payInfo.orderStatus(),
                orderProductDtos
        );    }
}
