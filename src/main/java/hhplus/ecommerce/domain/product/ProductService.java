package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    public Page<GetProductsByFilterInfo> getProductsByFilter(GetProductsByFilterCommand command , Pageable pageable) {

        return  productRepository.findByProductWithStockByFilter(command.productName() , command.minPrice() , command.maxPrice(),pageable).map(GetProductsByFilterInfo::from);
    }

    public List<PopularProductDto> getPopularProduct3Days() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        Pageable three = PageRequest.of(0,3);

        return orderRepository.findPoplarProductBeforeDays(startDate,endDate,three);
    }
}
