package hhplus.ecommerce.infra.point;

import hhplus.ecommerce.domain.point.IPointRepository;
import hhplus.ecommerce.domain.point.model.Point;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class PointRepositoryImpl implements IPointRepository {
    private final JpaPointRepository jpaPointRepository;

    public PointRepositoryImpl(JpaPointRepository jpaPointRepository) {
        this.jpaPointRepository = jpaPointRepository;
    }

    @Override
    public Optional<Point> findByUserId(long userId) {
        return jpaPointRepository.findByUserId(userId);
    }

    @Override
    public Point save(Point point) {
        return jpaPointRepository.save(point);
    }
}
