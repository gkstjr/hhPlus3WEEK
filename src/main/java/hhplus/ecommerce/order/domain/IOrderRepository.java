package hhplus.ecommerce.order.domain;

import hhplus.ecommerce.order.domain.model.Order;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {

    Order save(Order order);

    void deleteAll();

    Optional<Order> findById(long orderId);

    List<Order> saveAll(List<Order> orders);

    List<Order> findAll();
}
