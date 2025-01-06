package hhplus.ecommerce.infra.point;

import hhplus.ecommerce.domain.point.model.Point;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaPointRepository extends JpaRepository<Point , Long> {

    Optional<Point> findByUserId(long userId);
}
