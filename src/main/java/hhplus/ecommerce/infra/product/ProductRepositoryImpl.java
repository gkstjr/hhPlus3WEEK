package hhplus.ecommerce.infra.product;

import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {
    private final JpaProductRepository jpaProductRepository;
    private final JpaStockRepository jpaStockRepository;
    private final QueryDslRepository queryDslRepository;

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

    //상품재고
    @Override
    public void deleteAllStock() {
        jpaStockRepository.deleteAll();
    }

    @Override
    public Map<Long, ProductStock> findAllByProductIdInWithLock(List<Long> produceIds) {
        List<ProductStock> stocks = jpaStockRepository.findAllByProductIdInWithLock(produceIds);

        if (stocks.isEmpty()) {
            throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);
        }

        return stocks.stream()
                .collect(Collectors.toMap(stock -> stock.getProduct().getId(), Function.identity()));
    }
}
