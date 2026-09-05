package com.lashmanager.fichas.application.usecase;

import com.lashmanager.fichas.domain.exception.LashMappingNotFoundException;
import com.lashmanager.fichas.domain.port.in.DeleteLashMappingUseCase;
import com.lashmanager.fichas.domain.port.out.LashMappingRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteLashMappingUseCaseImpl implements DeleteLashMappingUseCase {

    private final LashMappingRepository mappingRepository;

    @Override
    public void execute(UUID id) {
        if (mappingRepository.findById(id).isEmpty()) {
            throw new LashMappingNotFoundException(id);
        }
        mappingRepository.delete(id);
    }
}
