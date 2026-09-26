package com.bravapro.settings.application.query;

import com.bravapro.settings.domain.exception.BusinessUnitNotFoundException;
import com.bravapro.settings.domain.model.BusinessUnit;
import com.bravapro.settings.domain.port.in.BusinessUnitQueryService;
import com.bravapro.settings.domain.port.out.BusinessUnitQueryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessUnitQueryServiceImpl implements BusinessUnitQueryService {

    private final BusinessUnitQueryRepository businessUnitQueryRepository;

    @Override
    public BusinessUnit getMain() {
        return businessUnitQueryRepository.findMain().orElseThrow(BusinessUnitNotFoundException::new);
    }
}
