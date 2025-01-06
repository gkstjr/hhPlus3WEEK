package hhplus.ecommerce.infra.user;

import hhplus.ecommerce.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaUserRepository extends JpaRepository<User,Long> {

}
