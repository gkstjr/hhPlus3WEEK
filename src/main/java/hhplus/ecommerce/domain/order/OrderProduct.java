package hhplus.ecommerce.domain.order;

import hhplus.ecommerce.support.BaseEntity;
import hhplus.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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

    @Builder
    public OrderProduct(Long id, Order order, Product product, int quantity, int price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
