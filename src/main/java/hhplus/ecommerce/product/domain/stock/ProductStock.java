package hhplus.ecommerce.product.domain.stock;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.product.domain.model.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stock;
    @OneToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;

    public void decreaseStock(int quantity) {
        validatedStock(quantity);
        this.stock -= quantity;
    }

    private void validatedStock(int quantity) {
        if(stock < quantity) throw new BusinessException(ErrorCode.OUT_OF_STOCK);
    }

    public void addProduct(Product product) {
        this.product = product;
    }
}
