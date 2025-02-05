package hhplus.ecommerce.domain.product;

import hhplus.ecommerce.support.BaseEntity;
import hhplus.ecommerce.domain.order.OrderProduct;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private long price;
    @OneToOne(mappedBy = "product" , cascade = CascadeType.ALL, orphanRemoval = true)
    private ProductStock productStock;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderProduct> orderProductList = new ArrayList<>();
    public Product(long id , String name , long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
    public void setProductStock(ProductStock productStock) {
        this.productStock = productStock;
        productStock.addProduct(this);
    }
    @Builder
    public Product(Long id, String name, long price, ProductStock productStock, List<OrderProduct> orderProductList) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.productStock = productStock;
        this.orderProductList = orderProductList != null ? orderProductList : new ArrayList<>();
    }
}
