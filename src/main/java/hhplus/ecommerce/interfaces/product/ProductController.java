package hhplus.ecommerce.interfaces.product;

import hhplus.ecommerce.domain.product.PopularProductDto;
import hhplus.ecommerce.domain.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "상품 조회", description = "상품 정보를 조회합니다.")
    @GetMapping()
    public ResponseEntity<Page<GetProductsResp>> getProducts(@ModelAttribute GetProductsReq getProductsReq) {
        return ResponseEntity.ok(productService.getProductsByFilter(getProductsReq.toCommand(),getProductsReq.toPageable()).map(GetProductsResp::from));
    }

    @Operation(summary = "인기 상품 조회", description = "가장 인기 있는 상위 3개의 상품을 조회합니다.")
    @GetMapping("/popular")
    public ResponseEntity<List<PopularProductDto>> getPopularProducts() {
        return ResponseEntity.ok(productService.getPopularProduct3Days());
    }


}
