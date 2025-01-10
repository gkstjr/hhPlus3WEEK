package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IIssuedCouponRepository;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.coupon.model.Coupon;
import hhplus.ecommerce.domain.order.dto.OrderCommand;
import hhplus.ecommerce.domain.order.dto.OrderInfo;
import hhplus.ecommerce.domain.order.dto.OrderItemDto;
import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.order.model.OrderProduct;
import hhplus.ecommerce.domain.product.model.Product;
import hhplus.ecommerce.domain.product.stock.IProductStockRepository;
import hhplus.ecommerce.domain.product.stock.ProductStock;
import hhplus.ecommerce.domain.user.IUserRepository;
import hhplus.ecommerce.domain.user.model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
/**
 * 나름 객체지향을 컨셉으로 Service단을 신경썼는데..
   코드가 절차지형적인지 궁금합니다...
    절차지향적이라면 피드백 해주시면 정말 감사하겠습니다.
 * */
@Service
public class OrderService {
    private final IOrderRepository iOrderRepository;
    private final IProductStockRepository iProductStockRepository;
    private final IUserRepository iUserRepository;
    private final IIssuedCouponRepository issuedCouponRepository;
    public OrderService(IOrderRepository iOrderRepository, IProductStockRepository iProductStockRepository, IUserRepository iUserRepository, IIssuedCouponRepository issuedCouponRepository) {
        this.iOrderRepository = iOrderRepository;
        this.iProductStockRepository = iProductStockRepository;
        this.iUserRepository = iUserRepository;
        this.issuedCouponRepository = issuedCouponRepository;
    }
    @Transactional
    public OrderInfo order(OrderCommand command) {
        User user = iUserRepository.findById(command.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        Map<Long, ProductStock> getProductStocks = iProductStockRepository.findAllByProductIdInWithLock(
                command.orderItems().stream().map(OrderItemDto::productId).toList()
        );

        List<OrderProduct> orderProducts = command.orderItems().stream()
                .map(dto -> new OrderProduct(getProductStocks.get(dto.productId()).getProduct(),dto.quantity()))
                .toList();

        //주문처리
        Order order = Order.createOrder(user , orderProducts , getProductStocks);

        //쿠폰처리
        if(command.issuedCouponId() != null) {
            IssuedCoupon issuedCoupon = issuedCouponRepository.findByIdWithCoupon(command.issuedCouponId()).orElseThrow(() -> new BusinessException(ErrorCode.ISSUEDCOUPON_NOT_FOUND));
            order.useCoupon(issuedCoupon);
        }

        //주문 저장
        order = iOrderRepository.save(order);

        return OrderInfo.from(order);
    }
}