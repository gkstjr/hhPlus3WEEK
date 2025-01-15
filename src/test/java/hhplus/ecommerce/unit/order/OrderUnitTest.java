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
    public void 재고가_부족한_상품주문시_OutOfStock_커스텀예외반환() {
        //given
        User user = User.builder()
                    .name("기만석")
                    .build();

        Product product1 = createProduct(1,"상품1",5000);
        ProductStock productStock1 = createProductStock(10 , product1);

        Product product2 = createProduct(2,"상품2",5000);
        ProductStock productStock2 = createProductStock(3 , product1);

        List<OrderProduct> orderProducts = List.of(
                createOrderProduct(product1, 11),
                createOrderProduct(product2, 4)
        );

        Map<Long , ProductStock> getProductStocks = Map.of(
                product1.getId() , productStock1,
                product2.getId() , productStock2
        );
        //when
        //then
        assertThatThrownBy(() -> Order.createOrder(user,orderProducts,getProductStocks))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.OUT_OF_STOCK);
    }
    //쿠폰 완료 후 쿠폰 관련 테스트 -> 주문성공테스트에 쿠폰사용도 추가
    @Test
    public void 쿠폰사용주문시_이미사용한쿠폰이면_ALREADY_USE_COUPON() {

        IssuedCoupon issuedCoupon = builder()
                .status(CouponStatus.USED)
                .build();
        //when
        Order order = Order.builder().build();
        //then
        assertThatThrownBy(() -> order.useCoupon(issuedCoupon))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.ALREADY_USE_COUPON);
    }
    @Test
    public void 쿠폰사용주문시_유효기간이지났으면_COUPON_EXPIRED_ISSUE() {
        Coupon coupon = Coupon.builder()
                .validUntil(LocalDate.now().minusDays(1))
                .build();

        IssuedCoupon issuedCoupon = builder()
                .status(CouponStatus.UNUSED)
                .coupon(coupon)
                .build();
        //when
        Order order = Order.builder().build();
        //then
        assertThatThrownBy(() -> order.useCoupon(issuedCoupon))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.COUPON_EXPIRED_ISSUE);
    }
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

        Map<Long , ProductStock> getProductStocks = Map.of(
                product1.getId() , productStock1,
                product2.getId() , productStock2
        );
        //when
        Order order = Order.createOrder(user,orderProducts,getProductStocks);
        //then
        assertThat(user).isEqualTo(order.getUser());
        assertThat(order.getStatus()).isEqualTo(Order.OrderStatus.PAYMENT_PENDING);
        assertThat(order.getTotalAmount()).isEqualTo(65000);
    }
    @Test
    public void 쿠폰사용주문시_할인금액적용() {
        //given
        Coupon coupon = Coupon.builder()
                .discountPrice(5000)
                .validUntil(LocalDate.now().plusDays(1))
                .build();

        IssuedCoupon issuedCoupon = builder()
                .status(CouponStatus.UNUSED)
                .coupon(coupon)
                .build();
        Order order = Order.builder()
                .totalAmount(50000)
                .build();
        //when
        order.useCoupon(issuedCoupon);

        //then
        assertThat(order.getTotalAmount()).isEqualTo(45000);
        assertThat(issuedCoupon.getStatus()).isEqualTo(CouponStatus.USED);
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
