package com.lashmanager.core.domain.port.out;

import com.lashmanager.core.domain.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findByPasswordResetToken(String token);
    User save(User user);
    boolean existsByEmail(String email);
}
