package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.application.order.event.OrderOutbox;
import hhplus.ecommerce.domain.product.PopularProductDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    void deleteAll();

    Optional<Order> findById(long orderId);

    List<Order> saveAll(List<Order> orders);

    List<Order> findAll();

    //주문상품
    List<PopularProductDto> findPoplarProductBeforeDays(LocalDateTime startDate, LocalDateTime endDate, Pageable three);

    void saveAllOrderProduct(List<OrderProduct> orderProducts);

    //OUTBOX
    void saveOutbox(OrderOutbox orderOutbox);

    Optional<OrderOutbox> findByEventId(String eventId);

    List<OrderOutbox> findAllByStatus(OrderOutbox.OrderOutboxStatus orderOutboxStatus);
}
