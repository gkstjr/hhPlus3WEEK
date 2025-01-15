package hhplus.ecommerce.unit.coupon;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class CouponUnitTest {

    @Test
    public void 쿠폰발급시_선착순인원이_찼으면_COUPON_MAX_ISSUE() {
        //given
        User user = User.builder().build();
        Coupon issueCoupon = Coupon.builder()
                .issuedCount(20)
                .maxIssuedCount(20)
                .build();
        //when
        //then
        assertThatThrownBy(() -> issueCoupon.issue(user))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COUPON_MAX_ISSUE);
    }

    @Test
    public void 쿠폰발급시_유효기간이_지났으면_COUPON_MAX_ISSUE() {
        //given
        User user = User.builder().build();
        Coupon issueCoupon = Coupon.builder()
                .issuedCount(19)
                .validUntil(LocalDate.now().minusDays(1))
                .maxIssuedCount(20)
                .build();
        //when
        //then
        assertThatThrownBy(() -> issueCoupon.issue(user))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COUPON_EXPIRED_ISSUE);
    }

    @Test
    public void 쿠폰발급검증및성공() {
        //given
        User user = User.builder()
                .name("사용자1").build();
        int currentIssueCnt = 19;
        Coupon issueCoupon = Coupon.builder()
                .issuedCount(currentIssueCnt)
                .validUntil(LocalDate.now().plusDays(1))
                .maxIssuedCount(20)
                .build();

        //when
        IssuedCoupon result = issueCoupon.issue(user);

        //then
        assertThat(result.getCoupon().getIssuedCount()).isEqualTo(currentIssueCnt + 1);
    }

}
