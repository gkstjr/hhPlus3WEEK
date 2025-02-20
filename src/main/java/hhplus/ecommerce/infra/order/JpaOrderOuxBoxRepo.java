package hhplus.ecommerce.infra.order;

import hhplus.ecommerce.application.order.event.OrderOutbox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaOrderOuxBoxRepo extends JpaRepository<OrderOutbox,Long> {

    Optional<OrderOutbox> findByEventId(String eventId);

    List<OrderOutbox> findAllByStatus(OrderOutbox.OrderOutboxStatus orderOutboxStatus);
}
