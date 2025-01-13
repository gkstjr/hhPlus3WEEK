package hhplus.ecommerce.order.application.dto;

import hhplus.ecommerce.order.domain.dto.OrderItemDto;

import java.util.List;

public record OrderPayCriteria(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {

}
