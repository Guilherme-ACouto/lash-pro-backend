package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.exception.UserNotFoundException;
import com.bravapro.core.domain.model.SupportSession;
import com.bravapro.core.domain.model.Tenant;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.TenantSupportUseCase;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TokenPort;
import com.bravapro.core.domain.port.out.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Só a equipe da plataforma (e-mail do domínio Brava Pro) entra em assinaturas de terceiros. O
 * token emitido aponta pra assinatura escolhida; lá dentro ela tem acesso de administradora, e
 * toda ação continua auditada com o e-mail dela.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TenantSupportUseCaseImpl implements TenantSupportUseCase {

    private final PlatformAdminChecker platformAdminChecker;
    private final CurrentAccess currentAccess;
    private final UserRepository userRepository;
    private final TokenPort tokenPort;

    @Override
    public SupportSession enter(Tenant tenant) {
        platformAdminChecker.check();
        if (!tenant.isActive()) {
            throw new BusinessException("Esta assinatura está inativa");
        }

        User user = userRepository.findById(currentAccess.userId()).orElseThrow(UserNotFoundException::new);
        String tenantId = tenant.getId().toString();
        String supportTenantId = tenant.getId().equals(user.getTenantId()) ? null : tenantId;

        String accessToken = tokenPort.generateAccessToken(user.getEmail(), user.isAdmin(), tenantId, user.getTokenVersion());
        String refreshToken = tokenPort.generateRefreshToken(user.getEmail(), supportTenantId, user.getTokenVersion());

        if (log.isInfoEnabled()) {
            log.info("Suporte: {} entrou na assinatura {} ({})", user.getEmail(), tenant.getName(), tenant.getId());
        }
        return new SupportSession(accessToken, refreshToken, tenant.getId(), tenant.getName());
    }
}
