package hhplus.ecommerce.product.interfaces;

import hhplus.ecommerce.product.application.ProductService;
import hhplus.ecommerce.product.domain.dto.GetProductsByFilterCommand;
import hhplus.ecommerce.product.domain.dto.PopularProductDto;
import hhplus.ecommerce.product.domain.model.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "상품 조회", description = "상품 정보를 조회합니다.")
    @GetMapping()
    public ResponseEntity<Page<Product>> getProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) long minPrice,
            @RequestParam(required = false) long maxPrice,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page,size);
        return ResponseEntity.ok(productService.getProductsByFilter(new GetProductsByFilterCommand(productName,minPrice,maxPrice),pageable));
    }

    @Operation(summary = "인기 상품 조회", description = "가장 인기 있는 상위 3개의 상품을 조회합니다.")
    @GetMapping("/popular")
    public ResponseEntity<List<PopularProductDto>> getPopularProducts() {
        return ResponseEntity.ok(productService.getPopularProduct3Days());
    }
}
