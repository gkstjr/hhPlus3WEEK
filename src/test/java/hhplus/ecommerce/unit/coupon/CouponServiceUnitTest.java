package hhplus.ecommerce.unit.coupon;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.CouponService;
import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.coupon.IssueCouponCommand;
import hhplus.ecommerce.domain.coupon.IssueCouponInfo;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

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

}
