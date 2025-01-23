package hhplus.ecommerce.infra.point;

import hhplus.ecommerce.domain.point.PointRepository;
import hhplus.ecommerce.domain.point.Point;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class PointRepositoryImpl implements PointRepository {
    private final JpaPointRepository jpaPointRepository;

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

    @Override
    public List<Point> saveAll(List<Point> points) {
        return jpaPointRepository.saveAll(points);
    }
}
