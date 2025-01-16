package hhplus.ecommerce.domain.point;

import hhplus.ecommerce.domain.user.User;
import hhplus.ecommerce.support.exception.BusinessException;
import hhplus.ecommerce.support.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    @Transactional(readOnly = true)
    public UserPointInfo getUserPoint(long userId) {
        Point point = pointRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        return UserPointInfo.of(point);
    }

    @Transactional
    public UserPointInfo chargePoint(ChargePointCommand command) {
        Point point = pointRepository.findByUserId(command.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        point.charge(command.chargePoint());

        return UserPointInfo.of(point);
    }
}
