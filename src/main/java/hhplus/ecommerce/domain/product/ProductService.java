package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.domain.product.dto.GetProductsByFilterCommand;
import hhplus.ecommerce.domain.product.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final IProductRepository iProductRepository;

    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    public Page<Product> getProductsByFilter(GetProductsByFilterCommand command , Pageable pageable) {
        return  iProductRepository.findByProductWithStockByFilter(command.productName() , command.minPrice() , command.maxPrice(),pageable);
    }
}
