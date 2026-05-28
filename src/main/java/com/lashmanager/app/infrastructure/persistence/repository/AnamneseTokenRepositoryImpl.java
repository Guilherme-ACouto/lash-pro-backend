package com.lashmanager.app.infrastructure.persistence.repository;

import com.lashmanager.app.domain.model.AnamneseToken;
import com.lashmanager.app.domain.port.out.AnamneseTokenRepository;
import com.lashmanager.app.infrastructure.persistence.mapper.AnamneseTokenMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.Optional;

@Repository @RequiredArgsConstructor
public class AnamneseTokenRepositoryImpl implements AnamneseTokenRepository {
    private final AnamneseTokenJpaRepository jpaRepository;
    private final AnamneseTokenMapper mapper;

    @Override public AnamneseToken save(AnamneseToken token) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(token)));
    }

    @Override public Optional<AnamneseToken> findByToken(String token) {
        return jpaRepository.findByTokenAndUsedFalseAndExpiresAtAfter(token, LocalDateTime.now())
            .map(mapper::toDomain);
    }

    @Override @Transactional public void deleteExpired() {
        jpaRepository.deleteExpired(LocalDateTime.now());
    }
}
