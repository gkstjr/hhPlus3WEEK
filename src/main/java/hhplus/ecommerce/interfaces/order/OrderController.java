package hhplus.ecommerce.interfaces.order;

import hhplus.ecommerce.application.OrderPayFacade;
import hhplus.ecommerce.application.dto.OrderPayCriteria;
import hhplus.ecommerce.application.dto.OrderPayResult;
import hhplus.ecommerce.domain.order.OrderService;
import hhplus.ecommerce.domain.order.dto.OrderItemDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@Tag(name = "E-Commerce", description = "E-Commerce API 명세")
public class OrderController {
    private final OrderPayFacade orderPayFacade;

    public OrderController(OrderPayFacade orderPayFacade) {
        this.orderPayFacade = orderPayFacade;
    }

    @Operation(summary = "주문 생성", description = "사용자의 주문을 생성합니다.")
    @PostMapping()
    public ResponseEntity<OrderPayResult> createOrder(@RequestBody long userId, @RequestBody List<OrderItemDto> orderItems , @RequestBody long issuedCouponId) {
        return ResponseEntity.ok(orderPayFacade.orderPay(new OrderPayCriteria(userId,orderItems,issuedCouponId)));
    }

}
