package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.exception.ServiceNotFoundException;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import com.lashmanager.services.domain.port.in.GetServiceUseCase;
import com.lashmanager.services.domain.port.out.ServiceQueryRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetServiceUseCaseImpl implements GetServiceUseCase {

  private final ServiceQueryRepository serviceQueryRepository;

  @Override
  public CreateServiceUseCase.ServiceResult execute(UUID id) {
    return serviceQueryRepository
        .findById(id)
        .map(ServiceUseCaseMapper::toResult)
        .orElseThrow(() -> new ServiceNotFoundException(id));
  }
}
