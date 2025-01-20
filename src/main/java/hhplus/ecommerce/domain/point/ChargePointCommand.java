package hhplus.ecommerce.domain.point;

public record ChargePointCommand(
        long userId,
        long chargePoint
) {
}
