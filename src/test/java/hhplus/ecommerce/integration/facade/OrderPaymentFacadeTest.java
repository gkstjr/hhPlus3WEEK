package hhplus.ecommerce.integration.facade;

import hhplus.ecommerce.application.OrderPayFacade;
import hhplus.ecommerce.application.dto.OrderPayCriteria;
import hhplus.ecommerce.application.dto.OrderPayResult;
import hhplus.ecommerce.domain.coupon.ICouponRepository;
import hhplus.ecommerce.domain.coupon.issuedcoupon.CouponStatus;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.model.Coupon;
import hhplus.ecommerce.domain.dataPlatform.DataPlatformService;
import hhplus.ecommerce.domain.order.IOrderRepository;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.dto.OrderItemDto;
import hhplus.ecommerce.domain.order.model.OrderProduct;
import hhplus.ecommerce.domain.order.model.OrderStatus;
import hhplus.ecommerce.domain.payment.PaymentService;
import hhplus.ecommerce.domain.point.IPointRepository;
import hhplus.ecommerce.domain.point.model.Point;
import hhplus.ecommerce.domain.product.IProductRepository;
import hhplus.ecommerce.domain.product.model.Product;
import hhplus.ecommerce.domain.product.stock.ProductStock;
import hhplus.ecommerce.domain.user.IUserRepository;
import hhplus.ecommerce.domain.user.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

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
    private DataPlatformService dataPlatformService;
    @Autowired
    private IUserRepository iUserRepository;
    @Autowired
    private IProductRepository iProductRepository;
    @Autowired
    private ICouponRepository iCouponRepository;
    @Autowired
    private IIssuedCouponRepository iIssuedCouponRepository;
    @Autowired
    private IPointRepository iPointRepository;

    @BeforeEach
    public void cleanUp() {
        iIssuedCouponRepository.deleteAll();
        iPointRepository.deleteAll();
        iCouponRepository.deleteAll();
        iProductRepository.deleteAll();
        iUserRepository.deleteAll();
    }
    @Test
    public void 주문_결제_성공통합테스트() {
        // Given: 테스트 데이터 준비
        //given
        User user = iUserRepository.save(
                User.builder()
                        .name("기만석")
                        .build()
        );
        Point point = iPointRepository.save(
                Point.builder()
                        .point(100000)
                        .user(user)
                        .build());

        List<Product> products = iProductRepository.saveAll(List.of(
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
        iCouponRepository.save(coupon);

        IssuedCoupon issuedCoupon =  iIssuedCouponRepository.save(
                 IssuedCoupon.builder()
                .status(CouponStatus.UNUSED)
                .coupon(coupon)
                .build());
        //when
        OrderPayResult result = orderPayFacade.orderPay(new OrderPayCriteria(user.getId(),reqOrderItems, issuedCoupon.getId()));
        //then
        assertThat(result.discountAmount()).isEqualTo(5000);
        assertThat(result.totalAmount()).isEqualTo(95000);
        assertThat(result.remindPoint()).isEqualTo(5000);
        assertThat(result.orderStatus()).isEqualTo(OrderStatus.PAYMENT_COMPLETED);
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
