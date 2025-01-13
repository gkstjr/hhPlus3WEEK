package hhplus.ecommerce.product.domain.stock;

import java.util.List;
import java.util.Map;

public interface IProductStockRepository {
    void deleteAll();

    Map<Long, ProductStock> findAllByProductIdInWithLock(List<Long> produceIds);
}
