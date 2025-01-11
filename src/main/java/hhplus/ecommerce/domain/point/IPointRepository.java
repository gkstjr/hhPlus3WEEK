package hhplus.ecommerce.domain.point;

import hhplus.ecommerce.domain.point.model.Point;

import java.util.Optional;

public interface IPointRepository {
    Optional<Point> findByUserId(long userId);

    Point save(Point point);

    Optional<Point> findByUserIdWithLock(long userId);

    Optional<Point> findById(Long id);

    void deleteAll();
}
