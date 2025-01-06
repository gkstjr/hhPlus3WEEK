package hhplus.ecommerce.domain.user;

import hhplus.ecommerce.domain.user.model.User;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);
}
