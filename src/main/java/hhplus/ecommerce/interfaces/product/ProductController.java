package hhplus.ecommerce.interfaces.product;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class ProductController {

    @Operation(summary = "상품 조회", description = "상품 정보를 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<Map<String, Object>>> getProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer price,
            @RequestParam(required = false, defaultValue = "1") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        List<Map<String, Object>> products = new ArrayList<>();
        Map<String, Object> product = new HashMap<>();
        product.put("id", 1L);
        product.put("name", "항해플러스");
        product.put("price", 10000);
        products.add(product);
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "인기 상품 조회", description = "가장 인기 있는 상위 3개의 상품을 조회합니다.")
    @GetMapping("/popular")
    public ResponseEntity<List<Map<String, Object>>> getPopularProducts() {
        List<Map<String, Object>> products = new ArrayList<>();
        Map<String, Object> product = new HashMap<>();
        product.put("productId", 1L);
        product.put("name", "항해플러스");
        product.put("soldCount", 50);
        products.add(product);
        return ResponseEntity.ok(products);
    }
}
