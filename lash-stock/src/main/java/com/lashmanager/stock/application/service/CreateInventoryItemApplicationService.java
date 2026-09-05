package com.lashmanager.stock.application.service;

import com.lashmanager.stock.application.command.CreateInventoryItemCommand;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateInventoryItemApplicationService {

    private final CreateInventoryItemUseCase createInventoryItemUseCase;

    public CreateInventoryItemUseCase.InventoryItemResult when(CreateInventoryItemCommand command) {
        return createInventoryItemUseCase.execute(command.toDomainCommand());
    }
}
