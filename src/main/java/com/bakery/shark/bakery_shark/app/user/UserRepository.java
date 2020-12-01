package com.bakery.shark.bakery_shark.app.user;

import com.bakery.shark.bakery_shark.app.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    User findUserByEmail(String email);

    User findByActivationCode(String code);

    Set<User> findAllByActiveTrue();
}
