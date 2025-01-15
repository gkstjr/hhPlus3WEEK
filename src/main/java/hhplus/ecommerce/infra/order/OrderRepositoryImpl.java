package hhplus.ecommerce.infra.order;

import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.product.PopularProductDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {

    private final JpaOrderRepository jpaOrderRepository;
    private final JpaOrderProductRepo jpaOrderProductRepo;

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


    //주문상품
    @Override
    public List<PopularProductDto> findPoplarProductBeforeDays(LocalDateTime startDate, LocalDateTime endDate, Pageable three) {
        return jpaOrderProductRepo.findPoplarProductBeforeDays(startDate , endDate , three);
    }

    @Override
    public void saveAllOrderProduct(List<OrderProduct> orderProducts) {
        jpaOrderProductRepo.saveAll(orderProducts);
    }
}
