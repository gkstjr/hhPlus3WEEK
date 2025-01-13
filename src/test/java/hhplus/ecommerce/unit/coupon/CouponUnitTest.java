package hhplus.ecommerce.unit.coupon;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.coupon.domain.model.Coupon;
import hhplus.ecommerce.user.domain.model.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

public class CouponUnitTest {

    @Test
    public void 쿠폰발급시_선착순인원이_찼으면_COUPON_MAX_ISSUE() {
        //given
        User user = new User();
        Coupon issueCoupon = Coupon.builder()
                .issuedCount(20)
                .maxIssuedCount(20)
                .build();
        //when
        //then
        assertThatThrownBy(() -> IssuedCoupon.issue(issueCoupon,user))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.COUPON_MAX_ISSUE);
    }

    @Test
    public void 쿠폰발급시_유효기간이_지났으면_COUPON_MAX_ISSUE() {
        //given
        User user = new User();
        Coupon issueCoupon = Coupon.builder()
                .issuedCount(19)
                .validUntil(LocalDate.now().minusDays(1))
                .maxIssuedCount(20)
                .build();
        //when
        //then
        assertThatThrownBy(() -> IssuedCoupon.issue(issueCoupon,user))
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
        IssuedCoupon result = IssuedCoupon.issue(issueCoupon,user);

        //then
        assertThat(result.getCoupon().getIssuedCount()).isEqualTo(currentIssueCnt + 1);
    }

}
