package hhplus.ecommerce.infra.point;

import hhplus.ecommerce.domain.point.Point;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface JpaPointRepository extends JpaRepository<Point , Long> {

    Optional<Point> findByUserId(long userId);

    @Query("select p from Point p where p.user.id = :userId")
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Point> findByUserIdWithLock(long userId);
}
