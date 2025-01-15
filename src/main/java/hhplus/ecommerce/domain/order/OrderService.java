package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.domain.coupon.CouponRepository;
import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.domain.user.UserRepository;
import hhplus.ecommerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CouponRepository couponRepository;

    @Transactional
    public OrderInfo order(OrderCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Map<Long, ProductStock> getProductStocks = productRepository.findAllByProductIdInWithLock(
                command.orderItems().stream().map(OrderItemDto::productId).toList()
        );

        List<OrderProduct> orderProducts = command.orderItems().stream()
                .map(dto -> new OrderProduct(getProductStocks.get(dto.productId()).getProduct(),dto.quantity()))
                .toList();

        //주문처리
        Order order = Order.createOrder(user , orderProducts , getProductStocks);

        //쿠폰처리
        if(command.issuedCouponId() != null) {
            IssuedCoupon issuedCoupon = couponRepository.findByIssuedCouponIdWithCoupon(command.issuedCouponId()).orElseThrow(() -> new BusinessException(ErrorCode.ISSUEDCOUPON_NOT_FOUND));
            order.useCoupon(issuedCoupon);
        }

        //주문 저장
        order = orderRepository.save(order);

        return OrderInfo.from(order);
    }
}
