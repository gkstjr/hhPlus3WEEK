package hhplus.ecommerce.user.domain;

import hhplus.ecommerce.user.domain.model.User;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);

    Optional<User> findById(long id);

    void deleteAll();
}
