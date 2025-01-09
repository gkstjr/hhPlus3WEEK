package hhplus.ecommerce.domain.order.model;

import hhplus.ecommerce.common.BaseEntity;
import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.order.dto.OrderItemDto;
import hhplus.ecommerce.domain.product.model.Product;
import hhplus.ecommerce.domain.product.stock.ProductStock;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class OrderProduct extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;

    private int quantity;

    private int price;


    public OrderProduct(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public void addOrder(Order order) {
        this.order = order;
    }
}
