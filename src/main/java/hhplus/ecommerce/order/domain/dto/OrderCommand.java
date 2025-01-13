package hhplus.ecommerce.order.domain.dto;

import java.util.List;

public record OrderCommand(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {
}
