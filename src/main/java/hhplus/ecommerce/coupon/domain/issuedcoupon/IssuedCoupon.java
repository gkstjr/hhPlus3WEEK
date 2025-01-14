package hhplus.ecommerce.coupon.domain.issuedcoupon;

import hhplus.ecommerce.common.BaseEntity;
import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.coupon.domain.model.Coupon;
import hhplus.ecommerce.user.domain.model.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id","coupon_id"})
        }
)
public class IssuedCoupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne
    @JoinColumn(name = "coupon_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Coupon coupon;
    @Enumerated(EnumType.STRING)
    private CouponStatus status;

    public IssuedCoupon(Coupon getCoupon, User getUser) {
        this.coupon = getCoupon;
        this.user = getUser;
        this.status = CouponStatus.UNUSED;
    }


    public static IssuedCoupon issue(Coupon getCoupon, User getUser) {
        IssuedCoupon issuedCoupon = new IssuedCoupon();

        getCoupon.validateIssueCoupon();
        getCoupon.incrementIssuedCoupons();

        return new IssuedCoupon(getCoupon , getUser);
    }

    public void ValidatedUse(IssuedCoupon issuedCoupon) {
        if(status.equals(CouponStatus.USED)) throw new BusinessException(ErrorCode.ALREADY_USE_COUPON);
        issuedCoupon.coupon.validateDate();
    }

    public void statusUsed() {
        status = CouponStatus.USED;
    }
    //테스트용(setter는 최대한 지양하자...)
    public void setId(long id) {
        this.id = id;
    }

    @Builder
    public IssuedCoupon(Long id, User user, Coupon coupon, CouponStatus status) {
        this.id = id;
        this.user = user;
        this.coupon = coupon;
        this.status = status;
    }
}
