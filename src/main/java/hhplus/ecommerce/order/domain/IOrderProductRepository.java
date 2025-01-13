package hhplus.ecommerce.order.domain;

import hhplus.ecommerce.order.domain.model.OrderProduct;
import hhplus.ecommerce.product.domain.dto.PopularProductDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderProductRepository {
    List<PopularProductDto> findPoplarProductBeforeDays(LocalDateTime startDate, LocalDateTime endDate, Pageable three);

    void saveAll(List<OrderProduct> orderProducts);
}
