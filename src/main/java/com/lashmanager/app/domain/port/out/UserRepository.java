package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String token);
    User save(User user);
    boolean existsByEmail(String email);
}
