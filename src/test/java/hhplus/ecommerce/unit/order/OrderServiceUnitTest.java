package hhplus.ecommerce.unit.order;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderItemDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class OrderServiceUnitTest {

    @InjectMocks
    private OrderService orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private CouponRepository couponRepository;

    @Test
    public void 주문한_사용자가_없으면_USER_NOT_FOUND반환() {
        //given
        long findUserId = 1L;
        when(userRepository.findById(findUserId)).thenReturn(Optional.empty());

        //then
        //when
        assertThatThrownBy(()-> orderService.order(new OrderCommand(findUserId,List.of(),null)))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.USER_NOT_FOUND);
    }

    @Test
    public void 주문시_발급쿠폰ID가_유효하지_않으면_ISSUEDCOUPON_NOT_FOUND() {
        //given
        long userId = 1L;
        long issuedCouponId = 1L;
        List<OrderItemDto> orderitems = List.of(new OrderItemDto(1L , 2));
        OrderCommand command = new OrderCommand(userId,orderitems,issuedCouponId);

        User mockUser = mock(User.class);
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Product product = Product.builder().id(1L).build();

        OrderProduct orderProduct = new OrderProduct(product, 2);

        Map<Long, ProductStock> productStocks = Map.of(1L, ProductStock.builder().product(product).stock(10).build());
        when(productRepository.findAllByProductIdInWithLock(Mockito.anyList())).thenReturn(productStocks);
        when(couponRepository.findByIssuedCouponIdWithCoupon(issuedCouponId)).thenReturn(Optional.empty());

        //then
        //when
        assertThatThrownBy(() -> orderService.order(command))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ISSUEDCOUPON_NOT_FOUND);

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
