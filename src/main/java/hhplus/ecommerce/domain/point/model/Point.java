package hhplus.ecommerce.domain.point.model;

import hhplus.ecommerce.domain.common.BaseEntity;
import hhplus.ecommerce.domain.common.exception.BusinessException;
import hhplus.ecommerce.domain.common.exception.ErrorCode;
import hhplus.ecommerce.domain.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
}
