package com.bravapro.core.application.query;

import com.bravapro.core.domain.exception.UserNotFoundException;
import com.bravapro.core.domain.model.CurrentUserDetails;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.MeQueryService;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TenantRepository;
import com.bravapro.core.domain.port.out.UserRepository;

import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MeQueryServiceImpl implements MeQueryService {

    private final CurrentAccess currentAccess;
    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;

    @Override
    public CurrentUserDetails current() {
        User user = userRepository.findById(currentAccess.userId()).orElseThrow(UserNotFoundException::new);
        Optional<Tenant> tenant = tenantRepository.findById(currentAccess.tenantId());
        return new CurrentUserDetails(
                user.getId(),
                user.getName(),
                user.getEmail(),
                currentAccess.isAdmin(),
                tenant.map(t -> t.isOwner(user.getId())).orElse(false),
                currentAccess.isPlatformAdmin(),
                currentAccess.isSupportSession(),
                currentAccess.tenantId(),
                tenant.map(Tenant::getName).orElse(""),
                currentAccess.permissionKeys());
    }
}
