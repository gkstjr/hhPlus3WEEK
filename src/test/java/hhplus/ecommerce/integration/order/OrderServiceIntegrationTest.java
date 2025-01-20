package hhplus.ecommerce.integration.order;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.order.*;
import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static hhplus.ecommerce.domain.coupon.IssuedCoupon.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class OrderServiceIntegrationTest {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CouponRepository couponRepository;
    @Autowired
    PointRepository pointRepository;
    @BeforeEach
    public void setUp() {
        //삭제
        pointRepository.deleteAll();
        orderRepository.deleteAll();
//        couponRepository.deleteAllIssuedCoupon();
//        couponRepository.deleteAll();
        userRepository.deleteAll();
        productRepository.deleteAllStock();
        productRepository.deleteAll();
    }

    @Test
    public void 주문성공() {
        //given
        User user = User.builder()
                .name("기만석")
                .build();
        user = userRepository.save(user);

        List<Product> products = List.of(
                getProduct("상품1",5000,getProductStock(10)),
                getProduct("상품2",10000,getProductStock(5))
        );
        products = productRepository.saveAll(products);

        List<OrderProduct> orderItems = List.of(
                OrderProduct.builder().product(products.get(0)).quantity(10).price(products.get(0).getPrice()).build(),
                OrderProduct.builder().product(products.get(1)).quantity(5).price(products.get(1).getPrice()).build()
        );

        OrderCommand orderCommand = new OrderCommand(user, orderItems);
        //when
        Order result = orderService.order(orderCommand);

        //then
        assertThat(result.getTotalAmount()).isEqualTo(100000);
    }

    private static OrderProduct createOrderProduct(Product product1, int quantity) {
        return OrderProduct.builder()
                .product(product1).quantity(quantity).build();
    }

    private static ProductStock getProductStock(int stock) {
        return ProductStock.builder()
                .stock(stock)
                .build();
    }

    private static Product getProduct(String name , long price , ProductStock productStock) {
        Product product =      Product.builder()
                .name(name)
                .price(price)
                .productStock(productStock)
                .build();

        product.setProductStock(productStock);
        return product;
    }
}
