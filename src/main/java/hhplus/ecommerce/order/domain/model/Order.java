package hhplus.ecommerce.order.domain.model;

import hhplus.ecommerce.common.BaseEntity;
import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.coupon.domain.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.payment.domain.model.Payment;
import hhplus.ecommerce.product.domain.stock.ProductStock;
import hhplus.ecommerce.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<OrderProduct> orderProductList = new ArrayList<>();
    private long totalAmount;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne(mappedBy = "order",cascade = CascadeType.ALL , orphanRemoval = true)
    private Payment payment;

    @OneToOne
    @JoinColumn(name = "issued_coupon_id",foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private IssuedCoupon issuedCoupon;

    //쿠폰발급이력
    public static Order createOrder(User user, List<OrderProduct> orderProducts, Map<Long, ProductStock> getProductStocks) {
        Order order = new Order();
        order.user = user;
        order.status = OrderStatus.PAYMENT_PENDING;


        for(OrderProduct orderProduct : orderProducts) {
            ProductStock stock = getProductStocks.get(orderProduct.getProduct().getId());
            if(stock == null) throw new BusinessException(ErrorCode.PRODUCT_NOT_FOUND);

            stock.decreaseStock(orderProduct.getQuantity());

            order.totalAmount += stock.getProduct().getPrice() * orderProduct.getQuantity();
            order.addOrderProduct(orderProduct);
        }
        return order;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProductList.add(orderProduct);
        orderProduct.addOrder(this); // 양방향 연관관계 설정
    }

    public void useCoupon(IssuedCoupon issuedCoupon) {
        issuedCoupon.ValidatedUse(issuedCoupon);

        totalAmount-= issuedCoupon.getCoupon().getDiscountPrice();
        if(totalAmount < 0) totalAmount = 0;
        issuedCoupon.statusUsed();
        this.issuedCoupon = issuedCoupon;
    }

    public void completePay() {
        this.status = OrderStatus.PAYMENT_COMPLETED;
    }

    @Builder
    public Order(Long id, User user, List<OrderProduct> orderProductList, long totalAmount, OrderStatus status, Payment payment, IssuedCoupon issuedCoupon) {
        this.id = id;
        this.user = user;
        this.orderProductList = orderProductList;
        this.totalAmount = totalAmount;
        this.status = status;
        this.payment = payment;
        this.issuedCoupon = issuedCoupon;
    }
}
