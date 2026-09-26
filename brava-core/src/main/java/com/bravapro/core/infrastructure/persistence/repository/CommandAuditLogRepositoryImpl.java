package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.domain.model.CommandAuditLog;
import com.bravapro.core.domain.port.out.CommandAuditLogRepository;
import com.bravapro.core.infrastructure.persistence.mapper.CommandAuditLogMapper;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommandAuditLogRepositoryImpl implements CommandAuditLogRepository {

    private final CommandAuditLogJpaRepository jpaRepository;
    private final CommandAuditLogMapper mapper;

    @Override
    public CommandAuditLog save(CommandAuditLog commandAuditLog) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(commandAuditLog)));
    }

    @Override
    public boolean existsByCommandClass(String commandClass) {
        return jpaRepository.existsByCommandClass(commandClass);
    }

    @Override
    public Optional<CommandAuditLog> findLatestByCommandClass(String commandClass) {
        return jpaRepository
                .findTopByCommandClassOrderByExecutedAtDesc(commandClass)
                .map(mapper::toDomain);
    }
}
