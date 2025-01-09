package hhplus.ecommerce.domain.order.dto;

import lombok.Getter;

import java.util.List;

public record OrderCommand(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {
}
