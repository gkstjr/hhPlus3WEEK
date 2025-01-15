package hhplus.ecommerce.application.order;

public record OrderProductDto(
        long productId ,
        String name ,
        long quantity ,
        long price
) {
}
