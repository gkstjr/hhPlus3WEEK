package hhplus.ecommerce.application.dto;

public record OrderProductDto(
        long productId ,
        String name ,
        long quantity ,
        long price
) {
}
