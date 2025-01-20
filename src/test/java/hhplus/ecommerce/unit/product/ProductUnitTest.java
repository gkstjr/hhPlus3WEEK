package hhplus.ecommerce.unit.product;

import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProductUnitTest {

    @Test
    public void 재고부족_상품주문시_OutOfStock_커스텀예외반환() {
        //given
        User user = User.builder()
                .name("기만석")
                .build();

        Product product1 = createProduct(1,"상품1",5000);
        ProductStock productStock1 = createProductStock(10 , product1);

        //when
        //then
        assertThatThrownBy(() -> productStock1.decreaseStock(20))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OUT_OF_STOCK);
    }

    private static OrderProduct createOrderProduct(Product product1, int quantity) {
        return OrderProduct.builder()
                .product(product1).quantity(quantity).build();
    }

    private static ProductStock createProductStock(int stock , Product product) {
        return ProductStock.builder()
                .product(product)
                .stock(stock)
                .build();
    }

    private static Product createProduct(long id , String name , long price) {
        Product product =      Product.builder()
                .id(id)
                .name(name)
                .price(price)
                .build();

        return product;
    }
}
