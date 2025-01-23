package hhplus.ecommerce.integration.point;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.point.*;
import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class PointServiceIntegrationTest {
    @Autowired
    private PointService pointService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PointRepository pointRepository;
    @Autowired
    private CouponRepository couponRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void before() {
        //삭제
        pointRepository.deleteAll();
        orderRepository.deleteAll();
//        couponRepository.deleteAllIssuedCoupon();
//        couponRepository.deleteAll();
        productRepository.deleteAllStock();
        productRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void 동일사용자가_동시에포인트_충전_요청시_3번중1번성공_2번은_ObjectOptimisticLockingFailureException_반환후실패() throws InterruptedException {
        //given
        Point point = getPoint(10000);
        User user = userRepository.save(getUser("한석가모니",point));

        int threadCount = 3; //스레드 4개이상부터는 첫번쨰 충전로직의 트랜잭션이 끝난 후에 요청이 들어오는 경우가 있어 동시성 테스트 환경이 아니라고 생각해 3개로 정했습니다.
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCnt = new AtomicInteger();
        AtomicInteger failCnt = new AtomicInteger();

        //when
        for(int i = 0 ; i < threadCount; i++) {
            executorService.submit(() -> {
                try{
                    pointService.chargePoint(new ChargePointCommand(user.getId(),5000));
                    successCnt.incrementAndGet();
                }catch(ObjectOptimisticLockingFailureException e) {
                    failCnt.incrementAndGet();
                }finally {
                    latch.countDown();
                }
            });
        }
        executorService.shutdown();
        latch.await();

        UserPointInfo result = pointService.getUserPoint(user.getId());

        assertThat(result.point()).isEqualTo(15000);
        assertThat(successCnt.get()).isEqualTo(1);
        assertThat(failCnt.get()).isEqualTo(2);
    }

    @Test
    public void 동일사용자가_동시에포인트_사용과충전_요청시_3번중1번성공_2번은_ObjectOptimisticLockingFailureException_반환후실패() throws InterruptedException {
        //given
        Point point = getPoint(10000);
        User user = userRepository.save(getUser("한석가모니",point));

        int threadCount = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCnt = new AtomicInteger();
        AtomicInteger failCnt = new AtomicInteger();

        //when 제일 처음 요청만 성공 이외 요청은 실패
        for(int i = 0 ; i < threadCount; i++) {
            int index = i;
            executorService.submit(() -> {
                try{
                    if(index % 2 == 1)  pointService.chargePoint(new ChargePointCommand(user.getId(),5000));
                    else pointService.usePoint(new UsePointCommand(user,5000));
                    successCnt.incrementAndGet();
                }catch(ObjectOptimisticLockingFailureException e) {
                    failCnt.incrementAndGet();
                }finally {
                    latch.countDown();
                }
            });
        }
        executorService.shutdown();
        latch.await();

        UserPointInfo result = pointService.getUserPoint(user.getId());

        assertThat(successCnt.get()).isEqualTo(1);
        assertThat(failCnt.get()).isEqualTo(2);
    }
    @Test
    public void 포인트조회() {
        //given
        String name = "기만석";
        long currentPoint = 5000;
        Point point = getPoint(currentPoint);
        User user = userRepository.save(getUser(name , point));

        //when
        UserPointInfo result = pointService.getUserPoint(user.getId());
        //then
        assertThat(result.pointId()).isEqualTo(point.getId());
        assertThat(result.userId()).isEqualTo(user.getId());
        assertThat(result.point()).isEqualTo(point.getPoint());
    }
    @Test
    public void 포인트충전() {
        //given
        String name = "기만석";
        long currentPoint = 5000;
        long  chargePoint = 10000;
        Point point = getPoint(currentPoint);
        User user = userRepository.save(getUser(name,point));
        //when
        UserPointInfo result = pointService.chargePoint(new ChargePointCommand(user.getId(),chargePoint));
        //then
        assertThat(result.point()).isEqualTo(currentPoint + chargePoint);
        assertThat(result.pointId()).isEqualTo(point.getId());
        assertThat(result.userId()).isEqualTo(user.getId());
    }

    private static Point getPoint(long point) {
        Point returnPoint = Point.builder()
                .point(point)
                .build();
        return returnPoint;
    }

    private static User getUser(String name , Point point) {
        User user = User.builder()
                .name(name)
                .build();

        user.setPoint(point);
        return user;
    }
}
