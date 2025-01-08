package hhplus.ecommerce.domain.product.model;

import hhplus.ecommerce.common.BaseEntity;
import hhplus.ecommerce.domain.product.stock.ProductStock;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long price;
    @OneToOne(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductStock productStock;

    public void setProductStock(ProductStock productStock) {
        this.productStock = productStock;
        productStock.setProduct(this);
    }
}
