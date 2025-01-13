package hhplus.ecommerce.user.infra;

import hhplus.ecommerce.user.domain.IUserRepository;
import hhplus.ecommerce.user.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryImpl implements IUserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryImpl(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public User save(User user) {
        return jpaUserRepository.save(user);
    }

    @Override
    public Optional<User> findById(long id) {
        return jpaUserRepository.findById(id);
    }

    @Override
    public void deleteAll() {
        jpaUserRepository.deleteAll();
    }
}
