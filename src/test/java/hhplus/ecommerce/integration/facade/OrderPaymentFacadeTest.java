package hhplus.ecommerce.integration.facade;

import hhplus.ecommerce.application.order.OrderPayFacade;
import hhplus.ecommerce.application.order.OrderPayCriteria;
import hhplus.ecommerce.application.order.OrderPayResult;
import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.application.order.DataPlatform;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.OrderItemDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.point.Point;
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
public class OrderPaymentFacadeTest {

    @Autowired
    private OrderPayFacade orderPayFacade;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DataPlatform dataPlatform;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void cleanUp() {
        couponRepository.deleteAllIssuedCoupon();
        userRepository.deleteAll();
        couponRepository.deleteAll();
        productRepository.deleteAll();
    }
    @Test
    public void 주문_결제_성공통합테스트() {
        // Given: 테스트 데이터 준비
        //given
        User user = userRepository.save(
                User.builder()
                        .name("기만석")
                        .build()
        );
        Point point = pointRepository.save(
                Point.builder()
                        .point(100000)
                        .user(user)
                        .build());
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

        IssuedCoupon issuedCoupon =  couponRepository.saveIssuedCoupon(
                 builder()
                .status(CouponStatus.UNUSED)
                .coupon(coupon)
                .build());
        //when
        OrderPayResult result = orderPayFacade.orderPay(new OrderPayCriteria(user,reqOrderItems, issuedCoupon.getId()));
        //then
        assertThat(result.discountAmount()).isEqualTo(5000);
        assertThat(result.totalAmount()).isEqualTo(95000);
        assertThat(result.remindPoint()).isEqualTo(5000);
        assertThat(result.orderStatus()).isEqualTo(Order.OrderStatus.PAYMENT_COMPLETED);
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
