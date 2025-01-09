package hhplus.ecommerce.application.dto;

import hhplus.ecommerce.domain.order.dto.OrderInfo;
import hhplus.ecommerce.domain.order.dto.OrderItemDto;
import hhplus.ecommerce.domain.order.model.OrderProduct;
import hhplus.ecommerce.domain.order.model.OrderStatus;
import hhplus.ecommerce.domain.payment.dto.PayInfo;

import java.util.List;

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
