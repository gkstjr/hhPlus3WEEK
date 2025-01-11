package hhplus.ecommerce.infra.order;

import hhplus.ecommerce.domain.order.model.OrderProduct;
import hhplus.ecommerce.domain.product.dto.PopularProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface JpaOrderProductRepo extends JpaRepository<OrderProduct,Long> {
    @Query("SELECT new hhplus.ecommerce.domain.product.dto.PopularProductDto(p.id, p.name, SUM(op.quantity)) " +
            "FROM OrderProduct op " +
            "JOIN op.product p " +
            "WHERE op.createdAt >= :startDate AND op.createdAt < :endDate " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(op.quantity) DESC")
    List<PopularProductDto> findPoplarProductBeforeDays(LocalDateTime startDate, LocalDateTime endDate, Pageable three);

}
