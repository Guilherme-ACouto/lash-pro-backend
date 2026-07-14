package com.lashmanager.fichas.infrastructure.config;

import com.lashmanager.clients.domain.port.in.GetClientUseCase;
import com.lashmanager.fichas.application.usecase.*;
import com.lashmanager.fichas.domain.port.out.FichaRepository;
import com.lashmanager.fichas.domain.port.out.LashMappingRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FichasConfig {

    @Bean
    public CreateFichaUseCaseImpl createFichaUseCase(FichaRepository fichaRepo, GetClientUseCase getClientUseCase) {
        return new CreateFichaUseCaseImpl(fichaRepo, getClientUseCase);
    }

    @Bean
    public UpdateFichaUseCaseImpl updateFichaUseCase(FichaRepository fichaRepo) {
        return new UpdateFichaUseCaseImpl(fichaRepo);
    }

    @Bean
    public GetFichaUseCaseImpl getFichaUseCase(FichaRepository fichaRepo) {
        return new GetFichaUseCaseImpl(fichaRepo);
    }

    @Bean
    public ListFichasUseCaseImpl listFichasUseCase(FichaRepository fichaRepo) {
        return new ListFichasUseCaseImpl(fichaRepo);
    }

    @Bean
    public CreateLashMappingUseCaseImpl createLashMappingUseCase(
            LashMappingRepository mappingRepo, FichaRepository fichaRepo) {
        return new CreateLashMappingUseCaseImpl(mappingRepo, fichaRepo);
    }

    @Bean
    public UpdateLashMappingUseCaseImpl updateLashMappingUseCase(LashMappingRepository mappingRepo) {
        return new UpdateLashMappingUseCaseImpl(mappingRepo);
    }

    @Bean
    public ListLashMappingsUseCaseImpl listLashMappingsUseCase(LashMappingRepository mappingRepo) {
        return new ListLashMappingsUseCaseImpl(mappingRepo);
    }

    @Bean
    public DeleteLashMappingUseCaseImpl deleteLashMappingUseCase(LashMappingRepository mappingRepo) {
        return new DeleteLashMappingUseCaseImpl(mappingRepo);
    }
}
