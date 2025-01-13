package hhplus.ecommerce.order.infra;

import hhplus.ecommerce.order.domain.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaOrderRepository extends JpaRepository<Order, Long> {

}
