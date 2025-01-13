package hhplus.ecommerce.unit.order;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.order.domain.IOrderRepository;
import hhplus.ecommerce.order.application.OrderService;
import hhplus.ecommerce.order.domain.dto.OrderCommand;
import hhplus.ecommerce.order.domain.dto.OrderItemDto;
import hhplus.ecommerce.order.domain.model.OrderProduct;
import hhplus.ecommerce.product.domain.model.Product;
import hhplus.ecommerce.product.domain.stock.IProductStockRepository;
import hhplus.ecommerce.product.domain.stock.ProductStock;
import hhplus.ecommerce.user.domain.IUserRepository;
import hhplus.ecommerce.user.domain.model.User;
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
    private IOrderRepository iOrderRepository;
    @Mock
    private IUserRepository iUserRepository;
    @Mock
    private IProductStockRepository iProductStockRepository;
    @Mock
    private IIssuedCouponRepository issuedCouponRepository;

    @Test
    public void 주문한_사용자가_없으면_USER_NOT_FOUND반환() {
        //given
        long findUserId = 1L;
        when(iUserRepository.findById(findUserId)).thenReturn(Optional.empty());

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
        when(iUserRepository.findById(userId)).thenReturn(Optional.of(mockUser));

        Product product = Product.builder().id(1L).build();

        OrderProduct orderProduct = new OrderProduct(product, 2);

        Map<Long, ProductStock> productStocks = Map.of(1L, ProductStock.builder().product(product).stock(10).build());
        when(iProductStockRepository.findAllByProductIdInWithLock(Mockito.anyList())).thenReturn(productStocks);
        when(issuedCouponRepository.findByIdWithCoupon(issuedCouponId)).thenReturn(Optional.empty());

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
