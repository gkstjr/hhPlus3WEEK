package hhplus.ecommerce.domain.user;

import hhplus.ecommerce.support.BaseEntity;
import hhplus.ecommerce.domain.coupon.IssuedCoupon;
import hhplus.ecommerce.domain.order.Order;
import hhplus.ecommerce.domain.payment.Payment;
import hhplus.ecommerce.domain.point.Point;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToOne(mappedBy = "user" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Point point;

    @OneToMany(mappedBy = "user" , cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.PERSIST,orphanRemoval = true)
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(mappedBy = "user" , cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<IssuedCoupon> issuedCoupons = new ArrayList<>();

    public void setPoint(Point point) {
        this.point = point;
        point.addUser(this);
    }

    @Builder
    public User(Long id, String name, Point point, List<Order> orders, List<Payment> payments, List<IssuedCoupon> issuedCoupons) {
        this.id = id;
        this.name = name;
        this.point = point;
        this.orders = orders != null ? orders : new ArrayList<>();
        this.payments = payments != null ? payments : new ArrayList<>();
        this.issuedCoupons = issuedCoupons != null ? issuedCoupons : new ArrayList<>();
    }
}
