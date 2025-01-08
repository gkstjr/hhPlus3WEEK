package hhplus.ecommerce.infra.product.stock;

import hhplus.ecommerce.domain.product.stock.ProductStock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaStockRepository extends JpaRepository<ProductStock,Long> {
}
