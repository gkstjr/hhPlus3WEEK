package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int stock;
    @OneToOne
    @JoinColumn(name = "product_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Product product;

    public void decreaseStock(long quantity) {
        validatedStock(quantity);
        this.stock -= quantity;
    }

    private void validatedStock(long quantity) {
        if(stock < quantity) throw new BusinessException(ErrorCode.OUT_OF_STOCK);
    }

    public void addProduct(Product product) {
        this.product = product;
    }

    @Builder
    public ProductStock(Long id, int stock, Product product) {
        this.id = id;
        this.stock = stock;
        this.product = product;
    }
}
