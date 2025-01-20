package hhplus.ecommerce.unit.coupon;

import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.Coupon;
import hhplus.ecommerce.domain.user.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static hhplus.ecommerce.domain.coupon.IssuedCoupon.builder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    @Test
    public void 쿠폰사용주문시_이미사용한쿠폰이면_ALREADY_USE_COUPON() {

        IssuedCoupon issuedCoupon = builder()
                .status(IssuedCoupon.CouponStatus.USED)
                .build();
        //when
        //then
        assertThatThrownBy(issuedCoupon::validatedUse)
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode",ErrorCode.ALREADY_USE_COUPON);
    }

    @Test
    public void 쿠폰사용주문시_할인금액적용() {
        //given
        Coupon coupon = Coupon.builder()
                .discountPrice(5000)
                .validUntil(LocalDate.now().plusDays(1))
                .build();

        IssuedCoupon issuedCoupon = builder()
                .status(IssuedCoupon.CouponStatus.UNUSED)
                .coupon(coupon)
                .build();

        //when
        long discountPrice = issuedCoupon.validatedUse();

        //then
        assertThat(discountPrice).isEqualTo(5000);
        assertThat(issuedCoupon.getStatus()).isEqualTo(IssuedCoupon.CouponStatus.USED);
    }
}
