package hhplus.ecommerce.domain.coupon;

import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.BaseEntity;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @OneToMany(mappedBy = "coupon", cascade = CascadeType.ALL)
    private List<IssuedCoupon> issuedCoupons = new ArrayList<>();

    // 비즈니스 로직
    public IssuedCoupon issue(User getUser) {
        IssuedCoupon issuedCoupon = new IssuedCoupon();

        validateIssueCoupon();
        incrementIssuedCoupons();

        return new IssuedCoupon(this , getUser);

    }
    public void validateIssueCoupon() {
        if (issuedCount >= maxIssuedCount) throw new BusinessException(ErrorCode.COUPON_MAX_ISSUE);
        if (validUntil.isBefore(LocalDate.now())) throw new BusinessException(ErrorCode.COUPON_EXPIRED_ISSUE);
    }

    public void incrementIssuedCoupons() {
        issuedCount++;
    }

    // 테스트용 빌더
    @Builder
    public static Coupon create(Long id , String name, long discountPrice, int maxIssuedCount, LocalDate validUntil , int issuedCount) {
        Coupon coupon = new Coupon();
        coupon.id = id;
        coupon.name = name;
        coupon.discountPrice = discountPrice;
        coupon.maxIssuedCount = maxIssuedCount;
        coupon.validUntil = validUntil;
        coupon.issuedCount = issuedCount;
        return coupon;
    }

    public Long calculateIssuableCount() {
        return (long) (maxIssuedCount - issuedCount);
    }
}
