package com.bravapro.settings.application.usecase;

import com.bravapro.core.domain.exception.BusinessException;
import com.bravapro.core.domain.port.out.CurrentAccess;
import com.bravapro.core.domain.port.out.TenantRepository;
import com.bravapro.settings.application.command.UpdateBusinessUnitCommand;
import com.bravapro.settings.application.command.UploadBusinessUnitLogoCommand;
import com.bravapro.settings.domain.model.BusinessUnit;
import com.bravapro.settings.domain.port.in.BusinessUnitUseCase;
import com.bravapro.settings.domain.port.out.BusinessUnitLogoStorage;
import com.bravapro.settings.domain.port.out.BusinessUnitRepository;

import java.util.Set;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessUnitUseCaseImpl implements BusinessUnitUseCase {

    private static final Set<String> ALLOWED_LOGO_TYPES = Set.of("image/png", "image/jpeg", "image/webp");
    private static final long MAX_LOGO_BYTES = 2L * 1024 * 1024;

    private final BusinessUnitRepository businessUnitRepository;
    private final BusinessUnitLogoStorage logoStorage;
    private final TenantRepository tenantRepository;
    private final CurrentAccess currentAccess;

    @Override
    public void update(BusinessUnit businessUnit, UpdateBusinessUnitCommand command) {
        businessUnit.update(command);
        businessUnitRepository.save(businessUnit);

        // O nome da assinatura acompanha o nome fantasia da unidade principal (é o que aparece
        // no convite, na lista de assinaturas da plataforma e no cabeçalho).
        if (businessUnit.isMain()) {
            tenantRepository.findById(currentAccess.tenantId()).ifPresent(tenant -> {
                tenant.rename(businessUnit.getTradeName());
                tenantRepository.save(tenant);
            });
        }
    }

    @Override
    public BusinessUnit uploadLogo(BusinessUnit businessUnit, UploadBusinessUnitLogoCommand command) {
        if (command.getContent() == null || command.getContent().length == 0) {
            throw new BusinessException("Selecione uma imagem");
        }
        if (!ALLOWED_LOGO_TYPES.contains(command.getContentType())) {
            throw new BusinessException("O logo deve ser PNG, JPG ou WebP");
        }
        if (command.getContent().length > MAX_LOGO_BYTES) {
            throw new BusinessException("O logo deve ter no máximo 2 MB");
        }

        String previousKey = businessUnit.getLogoKey();
        BusinessUnitLogoStorage.StoredLogo stored =
                logoStorage.store(currentAccess.tenantId(), command.getContent(), command.getContentType());
        businessUnit.changeLogo(stored.key(), stored.url());
        BusinessUnit saved = businessUnitRepository.save(businessUnit);
        logoStorage.delete(previousKey);
        return saved;
    }

    @Override
    public void removeLogo(BusinessUnit businessUnit) {
        String previousKey = businessUnit.getLogoKey();
        businessUnit.removeLogo();
        businessUnitRepository.save(businessUnit);
        logoStorage.delete(previousKey);
    }
}
