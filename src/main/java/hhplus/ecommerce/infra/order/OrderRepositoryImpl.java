package hhplus.ecommerce.infra.order;

import hhplus.ecommerce.domain.order.IOrderRepository;
import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.product.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {

    private final JpaOrderRepository jpaOrderRepository;

    public OrderRepositoryImpl(JpaOrderRepository jpaOrderRepository) {
        this.jpaOrderRepository = jpaOrderRepository;
    }

    @Override
    public Order save(Order order) {
        return jpaOrderRepository.save(order);
    }

    @Override
    public void deleteAll() {
        jpaOrderRepository.deleteAll();
    }

    @Override
    public Optional<Order> findById(long orderId) {
        return jpaOrderRepository.findById(orderId);
    }

    @Override
    public List<Order> saveAll(List<Order> orders) {
        return jpaOrderRepository.saveAll(orders);
    }

    @Override
    public List<Order> findAll() {
        return jpaOrderRepository.findAll();
    }
}
