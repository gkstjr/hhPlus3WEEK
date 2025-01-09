package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.domain.order.model.Order;
import hhplus.ecommerce.domain.product.model.Product;

import java.util.List;
import java.util.Optional;

public interface IOrderRepository {

    Order save(Order order);

    void deleteAll();

    Optional<Order> findById(long orderId);

    List<Order> saveAll(List<Order> orders);

    List<Order> findAll();
}
