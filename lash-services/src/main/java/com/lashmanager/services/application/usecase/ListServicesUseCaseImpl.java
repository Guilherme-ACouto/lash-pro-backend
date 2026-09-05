package com.lashmanager.services.application.usecase;

import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import com.lashmanager.services.domain.port.in.ListServicesUseCase;
import com.lashmanager.services.domain.port.out.ServiceQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListServicesUseCaseImpl implements ListServicesUseCase {

  private final ServiceQueryRepository serviceQueryRepository;

  @Override
  public Page<CreateServiceUseCase.ServiceResult> execute(
      String search, Boolean active, Pageable pageable) {
    return serviceQueryRepository
        .findAll(search, active, pageable)
        .map(ServiceUseCaseMapper::toResult);
  }
}
