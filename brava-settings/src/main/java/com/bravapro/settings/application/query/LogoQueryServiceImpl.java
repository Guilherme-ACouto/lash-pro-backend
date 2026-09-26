package com.bravapro.settings.application.query;

import com.bravapro.settings.domain.port.in.LogoQueryService;
import com.bravapro.settings.domain.port.out.BusinessUnitLogoStorage;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoQueryServiceImpl implements LogoQueryService {

    private final BusinessUnitLogoStorage logoStorage;

    @Override
    public Optional<BusinessUnitLogoStorage.LogoFile> load(UUID tenantId, String fileName) {
        return logoStorage.load(tenantId, fileName);
    }
}
