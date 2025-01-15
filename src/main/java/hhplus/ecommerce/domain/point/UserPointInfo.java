package hhplus.ecommerce.domain.point;

public record UserPointInfo(
        long pointId,
        long userId,
        long point

) {
    public static UserPointInfo of(Point point) {
        return new UserPointInfo(point.getId(),point.getUser().getId(),point.getPoint());

    }
}
