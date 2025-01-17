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

    @Transactional
    public Order order(OrderCommand command) {

        Order order = Order.createOrder(command.user() , command.orderProducts());

        return orderRepository.save(order);
    }
}
