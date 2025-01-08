package hhplus.ecommerce.infra.product.stock;

import hhplus.ecommerce.domain.product.stock.IProductStockRepository;
import org.springframework.stereotype.Repository;

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
}
