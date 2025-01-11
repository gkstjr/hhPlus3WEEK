package hhplus.ecommerce.infra.order;

import hhplus.ecommerce.domain.order.IOrderProductRepository;
import hhplus.ecommerce.domain.order.model.OrderProduct;
import hhplus.ecommerce.domain.product.dto.PopularProductDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class IOrderProductRepositoryImpl implements IOrderProductRepository {
    private final JpaOrderProductRepo jpaOrderProductRepo;

    public IOrderProductRepositoryImpl(JpaOrderProductRepo jpaOrderProductRepo) {
        this.jpaOrderProductRepo = jpaOrderProductRepo;
    }

    @Override
    public List<PopularProductDto> findPoplarProductBeforeDays(LocalDateTime startDate, LocalDateTime endDate, Pageable three) {
        return jpaOrderProductRepo.findPoplarProductBeforeDays(startDate , endDate , three);
    }

    @Override
    public void saveAll(List<OrderProduct> orderProducts) {
        jpaOrderProductRepo.saveAll(orderProducts);
    }
}
