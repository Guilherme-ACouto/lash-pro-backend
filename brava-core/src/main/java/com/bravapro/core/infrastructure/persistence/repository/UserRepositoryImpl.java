package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.core.infrastructure.persistence.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

    @Override
    public Optional<User> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByPasswordResetToken(String token) {
        return jpaRepository.findByPasswordResetToken(token).map(mapper::toDomain);
    }

    @Override
    public Optional<User> findByActivationKey(String activationKey) {
        return jpaRepository.findByActivationKey(activationKey).map(mapper::toDomain);
    }

    @Override
    public List<User> findAllByTenantId(UUID tenantId) {
        return jpaRepository.findAllByTenantIdOrderByNameAsc(tenantId).stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long countActiveAdmins(UUID tenantId) {
        return jpaRepository.countByTenantIdAndAdminTrueAndActiveTrue(tenantId);
    }

    @Override
    public User save(User user) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(UUID id) {
        jpaRepository.deleteById(id);
    }
}
