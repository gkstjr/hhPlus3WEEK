package hhplus.ecommerce.unit.coupon;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.CouponService;
import hhplus.ecommerce.domain.coupon.ICouponRepository;
import hhplus.ecommerce.domain.coupon.dto.IssueCouponCommand;
import hhplus.ecommerce.domain.coupon.dto.IssueCouponInfo;
import hhplus.ecommerce.domain.coupon.issuedcoupon.CouponStatus;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.model.Coupon;
import hhplus.ecommerce.domain.user.IUserRepository;
import hhplus.ecommerce.domain.user.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CouponServiceUnitTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private ICouponRepository iCouponRepository;
    @Mock
    private IUserRepository iUserRepository;
    @Mock
    private IIssuedCouponRepository iIssuedCouponRepository;
    @Test
    public void 쿠폰발급시_조회쿠폰이없으면_COUPON_NOT_FOUND() {
        //given
        long couponId = 1;
        long userId = 1;

        Mockito.when(iCouponRepository.findByIdWithLock(couponId)).thenReturn(Optional.empty());

        //then
        //when
        assertThatThrownBy(() -> couponService.issueCoupon(new IssueCouponCommand(userId,couponId)))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.COUPON_NOT_FOUND);
    }

    @Test
    public void 쿠폰발급시_사용자가없으면_USER_NOT_FOUND() {
        //given
        long couponId = 1;
        long userId = 1;

        Mockito.when(iCouponRepository.findByIdWithLock(couponId)).thenReturn(Optional.of(new Coupon()));
        Mockito.when(iUserRepository.findById(userId)).thenReturn(Optional.empty());

        //then
        //when
        assertThatThrownBy(() -> couponService.issueCoupon(new IssueCouponCommand(userId,couponId)))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.USER_NOT_FOUND);
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

        Mockito.when(iCouponRepository.findByIdWithLock(coupon.getId())).thenReturn(Optional.of(coupon));
        Mockito.when(iUserRepository.findById(user.getId())).thenReturn(Optional.of(user));
        Mockito.when(iIssuedCouponRepository.save(Mockito.any(IssuedCoupon.class)))
                .thenAnswer(invocation -> {
                    IssuedCoupon issuedCoupon = invocation.getArgument(0);
                    issuedCoupon.setId(1L); // 수동으로 ID 설정
                    return issuedCoupon;
                });

        // when
        IssueCouponInfo result = couponService.issueCoupon(new IssueCouponCommand(user.getId(), coupon.getId()));

        // then
        assertThat(result.coupon().getIssuedCount()).isEqualTo(currentIssueCnt + 1);
    }

}
