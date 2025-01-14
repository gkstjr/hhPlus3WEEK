package hhplus.ecommerce.integration.order;

import hhplus.ecommerce.coupon.domain.ICouponRepository;
import hhplus.ecommerce.coupon.domain.issuedcoupon.CouponStatus;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.coupon.domain.model.Coupon;
import hhplus.ecommerce.order.domain.IOrderRepository;
import hhplus.ecommerce.order.domain.OrderService;
import hhplus.ecommerce.order.domain.dto.OrderCommand;
import hhplus.ecommerce.order.domain.dto.OrderInfo;
import hhplus.ecommerce.order.domain.dto.OrderItemDto;
import hhplus.ecommerce.order.domain.model.OrderProduct;
import hhplus.ecommerce.product.domain.IProductRepository;
import hhplus.ecommerce.product.domain.model.Product;
import hhplus.ecommerce.product.domain.stock.IProductStockRepository;
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
public class OrderServiceIntegrationTest {
    @Autowired
    OrderService orderService;
    @Autowired
    IOrderRepository iOrderRepository;
    @Autowired
    IProductStockRepository iProductStockRepository;
    @Autowired
    IUserRepository iUserRepository;
    @Autowired
    IProductRepository iProductRepository;
    @Autowired
    IIssuedCouponRepository iIssuedCouponRepository;
    @Autowired
    ICouponRepository iCouponRepository;

    @BeforeEach
    public void setUp() {
        //삭제
        iProductRepository.deleteAll();
        iOrderRepository.deleteAll();
        iUserRepository.deleteAll();
        iIssuedCouponRepository.deleteAll();
        iCouponRepository.deleteAll();
        iProductStockRepository.deleteAll();

    }

    @Test
    public void 주문성공_쿠폰x() {
        //given
        User user = User.builder()
                .name("기만석")
                .build();
        user = iUserRepository.save(user);

        List<Product> products = List.of(
                getProduct("상품1",5000,getProductStock(10)),
                getProduct("상품2",10000,getProductStock(5))
        );
        products = iProductRepository.saveAll(products);

        List<OrderItemDto> orderItems = List.of(
                new OrderItemDto(products.get(0).getId(),10),
                new OrderItemDto(products.get(1).getId(), 5)
        );

        OrderCommand orderCommand = new OrderCommand(user.getId(), orderItems , null);
        //when
        OrderInfo order = orderService.order(orderCommand);
        //then
        assertThat(order.totalAmount()).isEqualTo(100000);
    }

    @Test
    public void 주문성공_쿠폰o() {
        //given
        User user = iUserRepository.save(
                             User.builder()
                            .name("기만석")
                            .build()
                    );
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

        IssuedCoupon issuedCoupon =
                IssuedCoupon.builder()
                        .status(CouponStatus.UNUSED)
                        .coupon(coupon)
                        .build();
        iIssuedCouponRepository.save(issuedCoupon);

        OrderCommand command = new OrderCommand(user.getId(),reqOrderItems,issuedCoupon.getId());
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
