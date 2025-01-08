package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IProductRepository {
    Page<Product> findByProductWithStockByFilter(String name, Long minPrice, Long maxPrice , Pageable pageable);

    List<Product> saveAll(List<Product> products);

    void deleteAll();
}
