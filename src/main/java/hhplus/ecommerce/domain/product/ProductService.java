package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.domain.order.OrderPayDto;
import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;


    public Page<GetProductsByFilterInfo> getProductsByFilter(GetProductsByFilterCommand command, Pageable pageable) {

        return productRepository.findByProductWithStockByFilter(command.productName(), command.minPrice(), command.maxPrice(), pageable).map(GetProductsByFilterInfo::from);
    }

    public OrderProductsInfo getOrderProducts(OrderProductsCommand command) {
        List<Long> productIds = command.orderItems().stream().map(OrderPayDto::productId).toList();

        Map<Long, Product> getProducts = productRepository.findAllByProductIdIn(productIds);

        return new OrderProductsInfo(
                command.orderItems().stream()
                        .map(dto -> new OrderProduct(getProducts.get(dto.productId()),dto.quantity(),getProducts.get(dto.productId()).getPrice()))
                        .toList()
        );
    }
    @Transactional
    public void subtractStock(SubtractStockCommand command) {
        List<Long> productIds = command.orderProducts().stream().map(dto -> dto.getProduct().getId()).toList();

        Map<Long,ProductStock> getProductStocks = productRepository.findAllStockByProductIdInWithLock(productIds);

        command.orderProducts().forEach(orderProduct -> getProductStocks.get(orderProduct.getProduct().getId()).decreaseStock(orderProduct.getQuantity()));
    }
    @Cacheable(value = "popular_products", key = "'last_3_days'", cacheManager = "redisCacheManager")
    public List<PopularProductDto> getPopularProduct3Days() {
        LocalDateTime endDate = LocalDateTime.now();
        LocalDateTime startDate = endDate.minusDays(3);
        Pageable three = PageRequest.of(0, 3);

        return orderRepository.findPoplarProductBeforeDays(startDate, endDate, three);
    }
}
