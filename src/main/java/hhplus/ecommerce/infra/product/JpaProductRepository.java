package hhplus.ecommerce.infra.product;

import hhplus.ecommerce.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaProductRepository extends JpaRepository<Product , Long> {
}
