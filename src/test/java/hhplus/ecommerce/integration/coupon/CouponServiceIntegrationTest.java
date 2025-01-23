package hhplus.ecommerce.integration.coupon;

import hhplus.ecommerce.domain.order.OrderCommand;
import hhplus.ecommerce.domain.order.OrderInfo;
import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.product.Product;
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
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static hhplus.ecommerce.domain.coupon.IssuedCoupon.builder;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class CouponServiceIntegrationTest {
    @Autowired
    private CouponService couponService;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;
    @BeforeEach
    public void before() {
        couponRepository.deleteAllIssuedCoupon();
        couponRepository.deleteAll();
        userRepository.deleteAll();
    }
    @Test
    public void 쿠폰발급시_동일사용자가_동일한쿠폰발급하면_ALREADY_ISSUE_COUPON() {
        //given
        long couponId = 1;
        long userId = 1;
        Coupon coupon = couponRepository.save(
                Coupon.builder()
                        .issuedCount(5)
                        .validUntil(LocalDate.now().plusDays(1))
                        .maxIssuedCount(20).build());
        User user = userRepository.save(
                User.builder()
                        .name("사용자1").build());

        IssueCouponCommand command = new IssueCouponCommand(user , coupon.getId());
        IssuedCoupon issuedCoupon = new IssuedCoupon(coupon , user);

        couponRepository.save(coupon);
        userRepository.save(user);
        couponRepository.saveIssuedCoupon(issuedCoupon);

        //when
        //then
        assertThatThrownBy(() -> couponService.issueCoupon(command))
                .isInstanceOf(BusinessException.class)
                .hasFieldOrPropertyWithValue("errorCode", ErrorCode.ALREADY_ISSUE_COUPON);
    }
    @Test
    public void 쿠폰발급성공() {
        //given
        int currentIssued = 19;

        Coupon coupon = couponRepository.save(
                                     Coupon.builder()
                                    .issuedCount(currentIssued)
                                    .validUntil(LocalDate.now().plusDays(1))
                                    .maxIssuedCount(20).build());

        User user = userRepository.save(
                                     User.builder()
                                     .name("사용자1").build());

        //when
        IssueCouponInfo result = couponService.issueCoupon(new IssueCouponCommand(user,coupon.getId()));

        //then
        assertThat(result.issuedCount()).isEqualTo(currentIssued + 1);
    }


    @Test
    public void 동시_쿠폰발급시_발급수량을_초과하면_COUPON_MAX_ISSUE_예외가_발생한다() throws InterruptedException {
        // given
        int threadCount = 5;
        int maxIssuedCount = 3;
        Coupon coupon = couponRepository.save(
                Coupon.builder()
                        .issuedCount(0)
                        .maxIssuedCount(maxIssuedCount)
                        .validUntil(LocalDate.now().plusDays(1))
                        .build()
        );
        for (int i = 1; i <= threadCount; i++) {
            userRepository.save(User.builder().name("사용자" + i).build());
        }

        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        // when
        for (int i = 0; i < threadCount; i++) {
            Long userId = (long) (i + 1); // 각각 다른 사용자
            User user = User.builder().id(userId).build();
            executorService.submit(() -> {
                try {
                    couponService.issueCoupon(new IssueCouponCommand(user, coupon.getId()));
                    successCount.incrementAndGet();
                } catch (Exception e) {
                        failCount.incrementAndGet();
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await(5, TimeUnit.SECONDS);
        executorService.shutdown();

        //when, then
        assertThat(successCount.get()).isEqualTo(3);
        assertThat(failCount.get()).isEqualTo(2);

    }
}
