package hhplus.ecommerce.domain.product.stock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IProductStockRepository {
    void deleteAll();

    Map<Long, ProductStock> findAllByProductIdInWithLock(List<Long> produceIds);
}
