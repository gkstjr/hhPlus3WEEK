package hhplus.ecommerce.domain.product;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductRepository {
    Page<Product> findByProductWithStockByFilter(String name, Long minPrice, Long maxPrice , Pageable pageable);

    List<Product> saveAll(List<Product> products);

    void deleteAll();

    Product save(Product product);

    //상품재고
    void deleteAllStock();

    Map<Long, ProductStock> findAllByProductIdInWithLock(List<Long> produceIds);
}
