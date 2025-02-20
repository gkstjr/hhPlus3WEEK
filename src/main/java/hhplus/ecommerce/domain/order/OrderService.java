package hhplus.ecommerce.domain.order;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
