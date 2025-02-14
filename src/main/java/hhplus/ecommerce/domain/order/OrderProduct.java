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

    private long quantity;

    private long price;


    public OrderProduct(Product product, int quantity, long price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public void addOrder(Order order) {
        this.order = order;
    }
    public long calculateTotalPrice() {
        return (long) quantity * price;
    }

    @Builder
    public OrderProduct(Long id, Order order, Product product, int quantity, long price) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }
}
