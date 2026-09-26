package com.bravapro.core.infrastructure.persistence.repository;

import com.bravapro.core.domain.model.Collaborator;
import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.domain.port.out.CollaboratorRepository;
import com.bravapro.core.infrastructure.multitenancy.TenantSchemaNaming;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * JDBC com o schema do tenant qualificado no SQL (não JPA): a sessão do Hibernate é aberta com o
 * tenant do token no início da requisição, e aqui precisamos ler/gravar em tenant arbitrário —
 * no filtro de segurança (antes do tenant da sessão importar) e no aceite de convite (rota
 * pública, sessão aberta no public). O nome do schema vem do {@link TenantSchemaNaming} a partir
 * de um UUID, nunca de texto do usuário.
 */
@Repository
@RequiredArgsConstructor
public class CollaboratorRepositoryImpl implements CollaboratorRepository {

    private final JdbcTemplate jdbcTemplate;
    private final TenantSchemaNaming tenantSchemaNaming;

    @Override
    public Optional<Collaborator> findByUserId(UUID tenantId, UUID userId) {
        String schema = schema(tenantId);
        List<Collaborator> found = jdbcTemplate.query(
                "SELECT user_id, professional, created_at, updated_at FROM " + schema + ".collaborator WHERE user_id = ?",
                (rs, i) -> Collaborator.builder()
                        .userId(rs.getObject("user_id", UUID.class))
                        .professional(rs.getBoolean("professional"))
                        .createdAt(toLocalDateTime(rs.getTimestamp("created_at")))
                        .updatedAt(toLocalDateTime(rs.getTimestamp("updated_at")))
                        .build(),
                userId);
        if (found.isEmpty()) {
            return Optional.empty();
        }
        Collaborator collaborator = found.get(0);
        return Optional.of(collaborator.toBuilder()
                .permissions(toPermissions(findPermissionKeys(tenantId, userId)))
                .build());
    }

    @Override
    public List<Collaborator> findAll(UUID tenantId) {
        String schema = schema(tenantId);
        Map<UUID, Collaborator> byUser = new LinkedHashMap<>();
        jdbcTemplate.query(
                "SELECT user_id, professional, created_at, updated_at FROM " + schema + ".collaborator",
                rs -> {
                    UUID userId = rs.getObject("user_id", UUID.class);
                    byUser.put(userId, Collaborator.builder()
                            .userId(userId)
                            .professional(rs.getBoolean("professional"))
                            .permissions(EnumSet.noneOf(Permission.class))
                            .createdAt(toLocalDateTime(rs.getTimestamp("created_at")))
                            .updatedAt(toLocalDateTime(rs.getTimestamp("updated_at")))
                            .build());
                });
        jdbcTemplate.query("SELECT user_id, permission FROM " + schema + ".collaborator_permission", rs -> {
            Collaborator collaborator = byUser.get(rs.getObject("user_id", UUID.class));
            if (collaborator != null) {
                Permission.fromKey(rs.getString("permission")).ifPresent(collaborator.getPermissions()::add);
            }
        });
        return List.copyOf(byUser.values());
    }

    @Override
    public Set<String> findPermissionKeys(UUID tenantId, UUID userId) {
        return new HashSet<>(jdbcTemplate.queryForList(
                "SELECT permission FROM " + schema(tenantId) + ".collaborator_permission WHERE user_id = ?",
                String.class,
                userId));
    }

    @Override
    @Transactional
    public void save(UUID tenantId, Collaborator collaborator) {
        String schema = schema(tenantId);
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(
                "INSERT INTO " + schema + ".collaborator (user_id, professional, created_at, updated_at) "
                        + "VALUES (?, ?, ?, ?) "
                        + "ON CONFLICT (user_id) DO UPDATE SET professional = EXCLUDED.professional, updated_at = EXCLUDED.updated_at",
                collaborator.getUserId(),
                collaborator.isProfessional(),
                Timestamp.valueOf(collaborator.getCreatedAt() != null ? collaborator.getCreatedAt() : now),
                Timestamp.valueOf(now));
        jdbcTemplate.update(
                "DELETE FROM " + schema + ".collaborator_permission WHERE user_id = ?", collaborator.getUserId());
        for (Permission permission : collaborator.getPermissions()) {
            jdbcTemplate.update(
                    "INSERT INTO " + schema + ".collaborator_permission (user_id, permission) VALUES (?, ?)",
                    collaborator.getUserId(),
                    permission.key());
        }
    }

    @Override
    public void delete(UUID tenantId, UUID userId) {
        jdbcTemplate.update("DELETE FROM " + schema(tenantId) + ".collaborator WHERE user_id = ?", userId);
    }

    private String schema(UUID tenantId) {
        return tenantSchemaNaming.schemaNameFor(tenantId);
    }

    private static Set<Permission> toPermissions(Set<String> keys) {
        Set<Permission> permissions = EnumSet.noneOf(Permission.class);
        keys.forEach(key -> Permission.fromKey(key).ifPresent(permissions::add));
        return permissions;
    }

    private static LocalDateTime toLocalDateTime(Timestamp timestamp) {
        return timestamp != null ? timestamp.toLocalDateTime() : null;
    }
}
