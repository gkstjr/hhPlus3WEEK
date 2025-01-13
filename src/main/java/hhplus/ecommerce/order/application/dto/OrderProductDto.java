package hhplus.ecommerce.order.application.dto;

public record OrderProductDto(
        long productId ,
        String name ,
        long quantity ,
        long price
) {
}
