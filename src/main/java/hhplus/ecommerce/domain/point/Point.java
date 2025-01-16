package hhplus.ecommerce.domain.point;

import hhplus.ecommerce.support.BaseEntity;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import hhplus.ecommerce.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    private long point;
    private static final long MIN_CHARGE_POINT = 1000;
    private static final long MAX_CHARGE_POINT = 10000000;
    private static final long MAX_POINT = 100000000; //최대 포인트 보유한도 1억
    public void charge(long chargePoint) {
        if(chargePoint < MIN_CHARGE_POINT || chargePoint > MAX_CHARGE_POINT) throw new BusinessException(ErrorCode.INVALID_POINT_AMOUNT);
        isMaxPoint(this.point + chargePoint);

        this.point += chargePoint;
    }

    public void isMaxPoint(long point) {
        if(point > MAX_POINT) throw new BusinessException(ErrorCode.OVER_POINT_LIMIT);
    }

    public void subtractPoint(long totalAmount) {
        validateOverPoint(totalAmount);
        point -= totalAmount;
        System.out.println("포인트 = " + point);
    }

    private void validateOverPoint(long amount) {
        if(amount > point) throw new BusinessException(ErrorCode.OUT_OF_POINT);
    }

    @Builder
    public Point(Long id, User user, long point) {
        this.id = id;
        this.user = user;
        this.point = point;
    }

    public void addUser(User user) {
        this.user = user;
    }
}
