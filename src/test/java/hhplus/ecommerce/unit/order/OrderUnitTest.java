package hhplus.ecommerce.unit.order;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static hhplus.ecommerce.domain.coupon.IssuedCoupon.*;
import static org.assertj.core.api.Assertions.*;

public class OrderUnitTest {

    @Test
    public void 주문성공() {
        //given
        User user = User.builder()
                .name("기만석")
                .build();

        Product product1 = createProduct(1,"상품1",5000);
        ProductStock productStock1 = createProductStock(10 , product1);

        Product product2 = createProduct(2,"상품2",5000);
        ProductStock productStock2 = createProductStock(3 , product1);

        List<OrderProduct> orderProducts = List.of(
                createOrderProduct(product1, 10),
                createOrderProduct(product2, 3)
        );

        //when
        Order order = Order.createOrder(user,orderProducts);
        //then
        assertThat(user).isEqualTo(order.getUser());
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PAYMENT_PENDING);
        assertThat(order.getTotalAmount()).isEqualTo(65000);
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
