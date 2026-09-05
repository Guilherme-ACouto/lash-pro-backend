package com.lashmanager.fichas.application.service;

import com.lashmanager.fichas.application.command.CreateFichaCommand;
import com.lashmanager.fichas.domain.port.in.CreateFichaUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateFichaApplicationService {

  private final CreateFichaUseCase createFichaUseCase;

  public CreateFichaUseCase.FichaResult when(CreateFichaCommand command) {
    return createFichaUseCase.execute(command.toDomainCommand());
  }
}
