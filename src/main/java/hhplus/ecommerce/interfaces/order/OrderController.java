package hhplus.ecommerce.interfaces.order;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class OrderController {
    @Operation(summary = "주문 생성", description = "사용자의 주문을 생성합니다.")
    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestBody Map<String, Object> request) {
        Long userId = (Long) request.get("userId");
        List<Map<String, Object>> items = (List<Map<String, Object>>) request.get("items");
        return ResponseEntity.status(201).body("주문 생성 완료: " + 1L);
    }

}
