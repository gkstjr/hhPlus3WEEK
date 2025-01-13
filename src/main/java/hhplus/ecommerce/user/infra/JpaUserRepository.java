package hhplus.ecommerce.user.infra;

import hhplus.ecommerce.user.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User,Long> {

}
