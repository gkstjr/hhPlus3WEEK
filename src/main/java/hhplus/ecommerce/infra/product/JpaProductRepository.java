package hhplus.ecommerce.infra.product;

import hhplus.ecommerce.domain.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaProductRepository extends JpaRepository<Product , Long> {
    @Query("SELECT p FROM Product p where p.id in :productIds")
    List<Product> findAllByIdIn(List<Long> productIds);
}
