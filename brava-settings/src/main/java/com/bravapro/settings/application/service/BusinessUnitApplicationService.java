package com.bravapro.settings.application.service;

import com.bravapro.settings.application.command.RemoveBusinessUnitLogoCommand;
import com.bravapro.settings.application.command.UpdateBusinessUnitCommand;
import com.bravapro.settings.application.command.UploadBusinessUnitLogoCommand;
import com.bravapro.settings.domain.exception.BusinessUnitNotFoundException;
import com.bravapro.settings.domain.model.BusinessUnit;
import com.bravapro.settings.domain.port.in.BusinessUnitUseCase;
import com.bravapro.settings.domain.port.out.BusinessUnitRepository;

import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessUnitApplicationService {

    private final BusinessUnitUseCase businessUnitUseCase;
    private final BusinessUnitRepository businessUnitRepository;

    public void when(UpdateBusinessUnitCommand command) {
        businessUnitUseCase.update(getOne(command.getId()), command);
    }

    public BusinessUnit when(UploadBusinessUnitLogoCommand command) {
        return businessUnitUseCase.uploadLogo(getOne(command.getId()), command);
    }

    public void when(RemoveBusinessUnitLogoCommand command) {
        businessUnitUseCase.removeLogo(getOne(command.getId()));
    }

    private BusinessUnit getOne(UUID id) {
        return businessUnitRepository.findById(id).orElseThrow(BusinessUnitNotFoundException::new);
    }
}
