package hhplus.ecommerce.domain.payment;

import hhplus.ecommerce.support.BaseEntity;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.point.Point;
import hhplus.ecommerce.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Payment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "order_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Order order;

    @ManyToOne
    @JoinColumn(name ="user_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private long amount;

    public Payment(Order order, User user, long totalAmount) {
        this.order = order;
        this.user = user;
        this.amount = totalAmount;
    }

    public static Payment createPay(Order getOrder, Point getPoint) {
        getPoint.subtractPoint(getOrder.getTotalAmount());
        getPoint.getUser().addPoint(getPoint);

        getOrder.completePay();

        return new Payment(getOrder , getPoint.getUser() , getOrder.getTotalAmount());
    }
}
