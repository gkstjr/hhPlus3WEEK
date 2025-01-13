package hhplus.ecommerce.product.infra;

import hhplus.ecommerce.product.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product , Long> {
}
