package hhplus.ecommerce.unit.coupon;

import hhplus.ecommerce.domain.coupon.*;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static hhplus.ecommerce.domain.coupon.IssuedCoupon.builder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CouponServiceUnitTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;
    @Mock
    private UserRepository userRepository;
    @Test
    public void 쿠폰발급시_조회쿠폰이없으면_COUPON_NOT_FOUND() {
        //given
        long couponId = 1;
        long userId = 1;
        User user = User.builder().id(userId).build();

        Mockito.when(couponRepository.findByIdWithLock(couponId)).thenReturn(Optional.empty());

        //then
        //when
        assertThatThrownBy(() -> couponService.issueCoupon(new IssueCouponCommand(user,couponId)))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.COUPON_NOT_FOUND);
    }

    @Test
    public void 쿠폰발급성공() {
        //given
        int currentIssueCnt = 19;
        Coupon coupon = Coupon.builder()
                .id(1L)
                .issuedCount(currentIssueCnt)
                .validUntil(LocalDate.now().plusDays(1))
                .maxIssuedCount(20)
                .build();
        User user = User.builder()
                .id(1L)
                .name("사용자1").build();

        Mockito.when(couponRepository.findByIdWithLock(coupon.getId())).thenReturn(Optional.of(coupon));
        Mockito.when(couponRepository.saveIssuedCoupon(Mockito.any(IssuedCoupon.class)))
                .thenAnswer(invocation -> {
                    IssuedCoupon issuedCoupon = invocation.getArgument(0);
                    issuedCoupon.setId(1L); // 수동으로 ID 설정
                    return issuedCoupon;
                });

        // when
        IssueCouponInfo result = couponService.issueCoupon(new IssueCouponCommand(user, coupon.getId()));

        // then
        assertThat(result.issuedCount()).isEqualTo(currentIssueCnt + 1);
    }

    @Test
    public void 쿠폰사용시_발급쿠폰ID가_유효하지_않으면_ISSUEDCOUPON_NOT_FOUND() {
        //given
        long userId = 1L;
        User user = User.builder().id(userId).build();
        long issuedCouponId = 1L;
        UseCouponCommand command = new UseCouponCommand(issuedCouponId,user);

        User mockUser = mock(User.class);

        when(couponRepository.findByIssuedCouponIdWithCoupon(issuedCouponId,userId)).thenReturn(Optional.empty());

        //then
        //when
        assertThatThrownBy(() -> couponService.useCoupon(command))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ISSUEDCOUPON_NOT_FOUND);

    }
    @Test
    public void 쿠폰사용주문시_유효기간이지났으면_COUPON_EXPIRED_ISSUE() {
        Coupon coupon = Coupon.builder()
                .validUntil(LocalDate.now().minusDays(1))
                .build();

        IssuedCoupon issuedCoupon = builder()
                .status(IssuedCoupon.CouponStatus.UNUSED)
                .coupon(coupon)
                .build();
        //when
        Order order = Order.builder().build();
        //then
        assertThatThrownBy(issuedCoupon::validatedUse)
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.COUPON_EXPIRED_ISSUE);
    }
}
