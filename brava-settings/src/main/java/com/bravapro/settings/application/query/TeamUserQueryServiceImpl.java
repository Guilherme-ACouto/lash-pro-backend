package com.bravapro.settings.application.query;

import com.bravapro.core.domain.exception.UserNotFoundException;
import com.bravapro.core.domain.model.Collaborator;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.domain.port.out.CollaboratorRepository;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TenantRepository;
import com.bravapro.core.domain.port.out.UserRepository;
import com.bravapro.settings.domain.model.TeamUser;
import com.bravapro.settings.domain.port.in.TeamUserQueryService;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamUserQueryServiceImpl implements TeamUserQueryService {

    private final UserRepository userRepository;
    private final CollaboratorRepository collaboratorRepository;
    private final TenantRepository tenantRepository;
    private final CurrentAccess currentAccess;

    @Override
    public List<TeamUser> list() {
        UUID tenantId = currentAccess.tenantId();
        UUID ownerId = tenantRepository.findById(tenantId).map(Tenant::getOwnerUserId).orElse(null);
        Map<UUID, Collaborator> collaborators = collaboratorRepository.findAll(tenantId).stream()
                .collect(Collectors.toMap(Collaborator::getUserId, Function.identity()));
        return userRepository.findAllByTenantId(tenantId).stream()
                .map(user -> toTeamUser(user, Optional.ofNullable(collaborators.get(user.getId())), ownerId))
                .toList();
    }

    @Override
    public TeamUser getById(UUID id) {
        UUID tenantId = currentAccess.tenantId();
        User user = userRepository
                .findById(id)
                .filter(u -> u.belongsTo(tenantId))
                .orElseThrow(UserNotFoundException::new);
        UUID ownerId = tenantRepository.findById(tenantId).map(Tenant::getOwnerUserId).orElse(null);
        return toTeamUser(user, collaboratorRepository.findByUserId(tenantId, id), ownerId);
    }

    private static TeamUser toTeamUser(User user, Optional<Collaborator> collaborator, UUID ownerId) {
        Set<String> permissions = collaborator
                .map(c -> c.getPermissions().stream().map(Permission::key).collect(Collectors.toSet()))
                .orElse(Set.of());
        return new TeamUser(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isAdmin(),
                user.isActive(),
                user.getId().equals(ownerId),
                collaborator.map(Collaborator::isProfessional).orElse(false),
                permissions,
                user.getLastLoginAt(),
                user.getCreatedAt());
    }
}
