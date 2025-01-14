package hhplus.ecommerce.integration.facade;

import hhplus.ecommerce.order.application.OrderPayFacade;
import hhplus.ecommerce.order.application.dto.OrderPayCriteria;
import hhplus.ecommerce.order.application.dto.OrderPayResult;
import hhplus.ecommerce.coupon.domain.ICouponRepository;
import hhplus.ecommerce.coupon.domain.issuedcoupon.CouponStatus;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.coupon.domain.model.Coupon;
import hhplus.ecommerce.order.application.dataplatform.DataPlatformService;
import hhplus.ecommerce.order.domain.OrderService;
import hhplus.ecommerce.order.domain.dto.OrderItemDto;
import hhplus.ecommerce.order.domain.model.OrderProduct;
import hhplus.ecommerce.order.domain.model.OrderStatus;
import hhplus.ecommerce.payment.domain.PaymentService;
import hhplus.ecommerce.point.domain.IPointRepository;
import hhplus.ecommerce.point.domain.model.Point;
import hhplus.ecommerce.product.domain.IProductRepository;
import hhplus.ecommerce.product.domain.model.Product;
import hhplus.ecommerce.product.domain.stock.ProductStock;
import hhplus.ecommerce.user.domain.IUserRepository;
import hhplus.ecommerce.user.domain.model.User;
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
