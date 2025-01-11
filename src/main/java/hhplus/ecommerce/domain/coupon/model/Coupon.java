package hhplus.ecommerce.domain.coupon.model;

import hhplus.ecommerce.common.BaseEntity;
import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.domain.coupon.issuedcoupon.IssuedCoupon;
import hhplus.ecommerce.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private long discountPrice;
    private int maxIssuedCount;
    private int issuedCount;
    private LocalDate validUntil;

    @OneToMany(mappedBy = "coupon" , cascade = CascadeType.ALL)
    private List<IssuedCoupon> issuedCoupons = new ArrayList<>();

//    public Coupon issue(User user) {
//        validateAvailableCoupon();
//
//    }

    public void validateIssueCoupon() {
        validateCount();
        validateDate();
    }

    public void validateDate() {
        if(validUntil.isBefore(LocalDate.now())) throw new BusinessException(ErrorCode.COUPON_EXPIRED_ISSUE);
    }
    public void validateCount() {
        if(issuedCount >= maxIssuedCount) throw new BusinessException(ErrorCode.COUPON_MAX_ISSUE);
    }

    public void incrementIssuedCoupons() {
        issuedCount++;
    }
}
