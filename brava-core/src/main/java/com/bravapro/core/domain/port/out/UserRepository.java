package com.bravapro.core.domain.port.out;

import com.bravapro.core.domain.model.User;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findById(UUID id);

    Optional<User> findByEmail(String email);

    Optional<User> findByPasswordResetToken(String token);

    Optional<User> findByActivationKey(String activationKey);

    List<User> findAllByTenantId(UUID tenantId);

    long countActiveAdmins(UUID tenantId);

    User save(User user);

    boolean existsByEmail(String email);

    void deleteById(UUID id);
}
