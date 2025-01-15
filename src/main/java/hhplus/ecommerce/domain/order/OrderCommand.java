package hhplus.ecommerce.domain.order;

import java.util.List;

public record OrderCommand(
        long userId,
        List<OrderItemDto> orderItems,

        Long issuedCouponId
) {



}
