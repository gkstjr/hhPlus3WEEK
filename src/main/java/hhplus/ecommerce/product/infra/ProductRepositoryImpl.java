package hhplus.ecommerce.product.infra;

import hhplus.ecommerce.product.domain.IProductRepository;
import hhplus.ecommerce.product.domain.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
    private final JpaProductRepository jpaProductRepository;
    private final QueryDslRepository queryDslRepository;
    public ProductRepositoryImpl(JpaProductRepository jpaProductRepository, QueryDslRepository queryDslRepository) {
        this.jpaProductRepository = jpaProductRepository;
        this.queryDslRepository = queryDslRepository;
    }

    @Override
    public Page<Product> findByProductWithStockByFilter(String name, Long minPrice, Long maxPrice, Pageable pageable) {
        return queryDslRepository.findByProductWithStockByFilter(name, minPrice, maxPrice, pageable);
    }

    @Override
    public List<Product> saveAll(List<Product> products) {
        return jpaProductRepository.saveAll(products);
    }

    @Override
    public void deleteAll() {
        jpaProductRepository.deleteAll();
    }

    @Override
    public Product save(Product product) {
        return jpaProductRepository.save(product);
    }
}
