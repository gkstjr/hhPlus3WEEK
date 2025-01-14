package hhplus.ecommerce.point.interfaces.dto;

import hhplus.ecommerce.point.domain.dto.UserPointInfo;

public record UserPointResp(
        long pointId,
        long userId,
        long point
) {

    public static UserPointResp from(UserPointInfo info) {
        return new UserPointResp(info.pointId(),info.userId(), info.point());
    }
}
