package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.core.infrastructure.persistence.mapper.UserMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository jpaRepository;
    private final UserMapper mapper;

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
    public User save(User user) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(user)));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }
}
