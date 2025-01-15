package hhplus.ecommerce.integration.order;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderInfo;
import hhplus.ecommerce.domain.order.OrderItemDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        couponRepository.deleteAllIssuedCoupon();
        couponRepository.deleteAll();
        productRepository.deleteAllStock();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 주문성공_쿠폰x() {
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

        List<OrderItemDto> orderItems = List.of(
                new OrderItemDto(products.get(0).getId(),10),
                new OrderItemDto(products.get(1).getId(), 5)
        );

        OrderCommand orderCommand = new OrderCommand(user, orderItems , null);
        //when
        OrderInfo order = orderService.order(orderCommand);
        //then
        assertThat(order.totalAmount()).isEqualTo(100000);
    }

    @Test
    public void 주문성공_쿠폰o() {
        //given
        User user = userRepository.save(
                             User.builder()
                            .name("기만석")
                            .build()
                    );
        List<Product> products = productRepository.saveAll(List.of(
                                                    getProduct("상품1",5000,getProductStock(10)),
                                                    getProduct("상품2",10000,getProductStock(5))
                                ));

        List<OrderItemDto> reqOrderItems = List.of(
                new OrderItemDto(products.get(0).getId(),10),
                new OrderItemDto(products.get(1).getId(), 5)
        );
        Coupon coupon = Coupon.builder()
                .name("5000원 할인쿠폰")
                .discountPrice(5000)
                .issuedCount(10)
                .maxIssuedCount(20)
                .validUntil(LocalDate.now().plusDays(1))
                .build();
        couponRepository.save(coupon);

        IssuedCoupon issuedCoupon =
                builder()
                        .status(CouponStatus.UNUSED)
                        .coupon(coupon)
                        .build();
        couponRepository.saveIssuedCoupon(issuedCoupon);

        OrderCommand command = new OrderCommand(user,reqOrderItems,issuedCoupon.getId());
        //when
        OrderInfo result = orderService.order(command);
        //then
        assertThat(result.totalAmount()).isEqualTo(95000);
        assertThat(result.issuedCoupon().getStatus()).isEqualTo(CouponStatus.USED);

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
