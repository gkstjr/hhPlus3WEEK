package hhplus.ecommerce.integration.product;

import hhplus.ecommerce.domain.order.OrderProduct;
import hhplus.ecommerce.domain.order.OrderRepository;
import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.domain.product.GetProductsByFilterInfo;
import hhplus.ecommerce.domain.product.PopularProductDto;
import hhplus.ecommerce.domain.product.ProductService;
import hhplus.ecommerce.domain.product.GetProductsByFilterCommand;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ProductServiceIntegrationTest {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    EntityManager entityManager;
    @Autowired
    OrderRepository orderRepository;
    @BeforeEach
    public void setUp() {
        productRepository.deleteAll();
        productRepository.deleteAllStock();
    }
    @Test
    public void 상품이름_필터조건_조회성공() {
        //given
        int stock = 20;
        List<Product> products = List.of(
                getProduct("상품1",5000,getProductStock(stock)),
                getProduct("상품2",10000,getProductStock(stock)),
                getProduct("상품3",30000,getProductStock(stock)),
                getProduct("상품4",100000,getProductStock(stock)),
                getProduct("신발1",100000,getProductStock(stock)),
                getProduct("신발2",100000,getProductStock(stock)),
                getProduct("신발3",100000,getProductStock(stock))
        );
        String filterName = "상품";
        Long filterMinPrice = null;
        Long filterMaxPrice = null;
        Pageable pageable = PageRequest.of(0, 10);

        List<Product> savedProducts = productRepository.saveAll(products);

        //when
        Page<GetProductsByFilterInfo> result = productService.getProductsByFilter(new GetProductsByFilterCommand(filterName,filterMinPrice,filterMaxPrice), pageable);
        //then
        assertThat(result.getContent().size()).isEqualTo(4);
        assertThat(result.getContent())
                .extracting(GetProductsByFilterInfo::name)
                .containsExactlyInAnyOrder("상품1","상품2","상품3","상품4");
        assertThat(result.getContent().get(0).stockQuantity()).isEqualTo(20); //재고 정보 확인
    }

    @Test
    public void 상품가격_필터조건_조회성공() {
        //given
        int stock = 20;
        List<Product> products = List.of(
                getProduct("상품1",5000,getProductStock(stock)),
                getProduct("상품2",10000,getProductStock(stock)),
                getProduct("상품3",30000,getProductStock(stock)),
                getProduct("상품4",100000,getProductStock(stock)),
                getProduct("신발1",150000,getProductStock(stock)),
                getProduct("신발2",200000,getProductStock(stock)),
                getProduct("신발3",300000,getProductStock(stock))
        );
        String filterName = null;
        Long filterMinPrice = 30000L;
        Long filterMaxPrice = 200000L;

        Pageable pageable = PageRequest.of(0, 10);
        List<Product> savedProducts = productRepository.saveAll(products);

        //when
        Page<GetProductsByFilterInfo> result = productService.getProductsByFilter(new GetProductsByFilterCommand(filterName,filterMinPrice,filterMaxPrice), pageable);
        //then
        assertThat(result.getContent().size()).isEqualTo(4);
        assertThat(result.getContent())
                .extracting(GetProductsByFilterInfo::name)
                .containsExactlyInAnyOrder("상품3","상품4","신발1","신발2");
        assertThat(result.getContent().get(0).stockQuantity()).isEqualTo(stock); //재고 정보 확인
    }

    @Test
    public void 판매량상위3개조회성공() {
        // Given: 테스트 데이터 준비
        int stock = 20;

        // 상품 및 재고 생성
        Product product1 = productRepository.save(getProduct("상품1", 5000, getProductStock(stock)));
        Product product2 = productRepository.save(getProduct("상품2", 10000, getProductStock(stock)));
        Product product3 = productRepository.save(getProduct("상품3", 30000, getProductStock(stock)));
        Product product4 = productRepository.save(getProduct("상품4", 100000, getProductStock(stock)));

        // 판매 데이터 생성
        orderRepository.saveAllOrderProduct(List.of(
                new OrderProduct(product1, 10),
                new OrderProduct(product2, 20),
                new OrderProduct(product3, 15),
                new OrderProduct(product4, 5)
        ));

        // When: 서비스 호출
        List<PopularProductDto> result = productService.getPopularProduct3Days();

        // Then: 결과 검증
        assertThat(result.size()).isEqualTo(3);
        assertThat(result)
                .extracting(PopularProductDto::productId)
                .containsExactly(product2.getId(), product3.getId(), product1.getId());
        assertThat(result.get(0).totalQuantity()).isEqualTo(20);
        assertThat(result.get(1).totalQuantity()).isEqualTo(15);
        assertThat(result.get(2).totalQuantity()).isEqualTo(10);
    }

    private static ProductStock getProductStock(int stock) {
        return ProductStock.builder()
                .stock(stock)
                .build();
    }

    private static Product getProduct(String name , long price , ProductStock productStock) {
          Product product =      Product.builder()
                  .name(name)
                  .price(price)
                  .productStock(productStock)
                  .build();

          product.setProductStock(productStock);
     return product;
    }
}
