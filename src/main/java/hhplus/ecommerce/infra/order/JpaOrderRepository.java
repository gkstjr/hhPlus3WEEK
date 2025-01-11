package hhplus.ecommerce.infra.order;

import hhplus.ecommerce.domain.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {

}
