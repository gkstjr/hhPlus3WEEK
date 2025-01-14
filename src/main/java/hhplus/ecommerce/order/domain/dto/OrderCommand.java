package hhplus.ecommerce.order.domain.dto;

import hhplus.ecommerce.payment.domain.dto.PayCommand;

import java.util.List;

public record OrderCommand(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {



}
