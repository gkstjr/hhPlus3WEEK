package hhplus.ecommerce.infra.product.stock;

import hhplus.ecommerce.domain.product.stock.ProductStock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface JpaStockRepository extends JpaRepository<ProductStock,Long> {
    @Query("SELECT ps FROM ProductStock ps join fetch ps.product p where ps.product.id in :productIds")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<ProductStock> findAllByProductIdInWithLock(@Param("productIds") List<Long> produceIds);
}
