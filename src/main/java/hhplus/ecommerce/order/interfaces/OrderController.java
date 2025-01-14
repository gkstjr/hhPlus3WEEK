package hhplus.ecommerce.order.interfaces;

import hhplus.ecommerce.order.application.OrderPayFacade;
import hhplus.ecommerce.order.application.dto.OrderPayCriteria;
import hhplus.ecommerce.order.application.dto.OrderPayResult;
import hhplus.ecommerce.order.domain.dto.OrderItemDto;
import hhplus.ecommerce.order.interfaces.dto.OrderPayReq;
import hhplus.ecommerce.order.interfaces.dto.OrderPayResp;
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
    public ResponseEntity<OrderPayResp> createOrder(@ModelAttribute OrderPayReq orderPayReq) {
        return ResponseEntity.ok(OrderPayResp.from(orderPayFacade.orderPay(orderPayReq.toCriteria())));
    }

}
