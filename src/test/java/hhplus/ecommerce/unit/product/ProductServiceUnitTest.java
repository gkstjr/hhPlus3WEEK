package hhplus.ecommerce.unit.product;

import hhplus.ecommerce.domain.product.ProductRepository;
import hhplus.ecommerce.domain.product.ProductService;
import hhplus.ecommerce.domain.product.GetProductsByFilterCommand;
import hhplus.ecommerce.domain.product.GetProductsByFilterInfo;
import hhplus.ecommerce.domain.product.Product;
import hhplus.ecommerce.domain.product.ProductStock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceUnitTest {

    @InjectMocks
    private  ProductService productService;
    @Mock
    private ProductRepository productRepository;

    @Test
    public void 상품조회결과없으면_빈리스트반환() {
        //given
        String name = "아디다스";
        long minPrice = 1000;
        long maxPrice = 100000;

        Mockito.when(productRepository.findByProductWithStockByFilter(name , minPrice , maxPrice , Pageable.unpaged()))
                .thenReturn(Page.empty());
        //when
        Page<GetProductsByFilterInfo> result = productService.getProductsByFilter(new GetProductsByFilterCommand(name , minPrice ,maxPrice), Pageable.unpaged());
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void 상품전체조회성공() {
        //given
        ProductStock productStock = getProductStock(10);

        List<Product> products = List.of(
                getProduct("상품1",5000,productStock),
                getProduct("상품2",10000,productStock),
                getProduct("상품3",30000,productStock),
                getProduct("상품4",100000,productStock)
        );

        Page<Product> mockProducts = new PageImpl<>(products, PageRequest.of(0,10), products.size());

        Mockito.when(productRepository.findByProductWithStockByFilter(null , null ,null,PageRequest.of(0,10)))
                .thenReturn(mockProducts);

        //when
        Page<GetProductsByFilterInfo> result = productService.getProductsByFilter(new GetProductsByFilterCommand(null, null,null),PageRequest.of(0,10));

        //then
        assertThat(result.getContent().size()).isEqualTo(mockProducts.getContent().size());
    }

    private static ProductStock getProductStock(int stock) {
        return ProductStock.builder()
                .stock(stock)
                .build();
    }

    private static Product getProduct(String name , long price , ProductStock productStock) {
        return  Product.builder()
                .name(name)
                .price(price)
                .productStock(productStock)
                .build();
    }
}
