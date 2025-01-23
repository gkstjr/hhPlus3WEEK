package hhplus.ecommerce.domain.point;

import java.util.List;
import java.util.Optional;

public interface PointRepository {
    Optional<Point> findByUserId(long userId);

    Point save(Point point);

    Optional<Point> findByUserIdWithLock(long userId);

    Optional<Point> findById(Long id);

    void deleteAll();

    List<Point> saveAll(List<Point> points);
}
