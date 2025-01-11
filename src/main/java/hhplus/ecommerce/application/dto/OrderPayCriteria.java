package hhplus.ecommerce.application.dto;

import hhplus.ecommerce.domain.order.dto.OrderItemDto;

import java.util.List;

public record OrderPayCriteria(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {

}
