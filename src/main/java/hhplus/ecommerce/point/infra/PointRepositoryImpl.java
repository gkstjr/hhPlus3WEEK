package hhplus.ecommerce.point.infra;

import hhplus.ecommerce.point.domain.IPointRepository;
import hhplus.ecommerce.point.domain.model.Point;
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

    @Override
    public Optional<Point> findByUserIdWithLock(long userId) {
        return jpaPointRepository.findByUserIdWithLock(userId);
    }

    @Override
    public Optional<Point> findById(Long id) {
        return jpaPointRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        jpaPointRepository.deleteAll();
    }
}
