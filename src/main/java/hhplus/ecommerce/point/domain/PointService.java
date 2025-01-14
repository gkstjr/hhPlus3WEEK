package hhplus.ecommerce.point.domain;

import hhplus.ecommerce.common.exception.BusinessException;
import hhplus.ecommerce.common.exception.ErrorCode;
import hhplus.ecommerce.point.domain.IPointRepository;
import hhplus.ecommerce.point.domain.dto.ChargePointCommand;
import hhplus.ecommerce.point.domain.dto.UserPointInfo;
import hhplus.ecommerce.point.domain.model.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointService {
    private final IPointRepository iPointRepository;

    public PointService(IPointRepository iPointRepository) {
        this.iPointRepository = iPointRepository;
    }
    @Transactional(readOnly = true)
    public UserPointInfo getUserPoint(long userId) {
        Point point = iPointRepository.findByUserId(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        return UserPointInfo.of(point);
    }

    @Transactional
    public UserPointInfo chargePoint(ChargePointCommand command) {
        Point point = iPointRepository.findByUserId(command.userId())
                .orElseThrow(() -> new BusinessException(ErrorCode.POINT_NOT_FOUND));

        point.charge(command.chargePoint());

        return UserPointInfo.of(point);
    }
}
