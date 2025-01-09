package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.domain.order.model.OrderProduct;
import hhplus.ecommerce.domain.product.dto.PopularProductDto;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface IOrderProductRepository {
    List<PopularProductDto> findPoplarProductBeforeDays(LocalDateTime startDate, LocalDateTime endDate, Pageable three);

    void saveAll(List<OrderProduct> orderProducts);
}
