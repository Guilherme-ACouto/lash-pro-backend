package com.bravapro.settings.domain.port.in;

import com.bravapro.settings.application.command.UpdateBusinessUnitCommand;
import com.bravapro.settings.application.command.UploadBusinessUnitLogoCommand;
import com.bravapro.settings.domain.model.BusinessUnit;

public interface BusinessUnitUseCase {

    void update(BusinessUnit businessUnit, UpdateBusinessUnitCommand command);

    BusinessUnit uploadLogo(BusinessUnit businessUnit, UploadBusinessUnitLogoCommand command);

    void removeLogo(BusinessUnit businessUnit);
}
