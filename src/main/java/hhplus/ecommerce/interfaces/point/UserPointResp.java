package hhplus.ecommerce.interfaces.point;

import hhplus.ecommerce.domain.point.UserPointInfo;

public record UserPointResp(
        long pointId,
        long userId,
        long point
) {

    public static UserPointResp from(UserPointInfo info) {
        return new UserPointResp(info.pointId(),info.userId(), info.point());
    }
}
