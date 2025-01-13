package hhplus.ecommerce.product.infra.stock;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.product.domain.stock.IProductStockRepository;
import hhplus.ecommerce.product.domain.stock.ProductStock;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class StockRepositoryImpl implements IProductStockRepository {

    private final JpaStockRepository jpaStockRepository;


    public StockRepositoryImpl(JpaStockRepository jpaStockRepository) {
        this.jpaStockRepository = jpaStockRepository;
    }


    @Override
    public void deleteAll() {
        jpaStockRepository.deleteAll();
    }
    //Service 계층의 단순화를 위해 검증책임
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
