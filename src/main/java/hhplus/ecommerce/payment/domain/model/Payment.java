package hhplus.ecommerce.payment.domain.model;

import hhplus.ecommerce.common.BaseEntity;
import hhplus.ecommerce.order.domain.model.Order;
import hhplus.ecommerce.point.domain.model.Point;
import hhplus.ecommerce.user.domain.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
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
