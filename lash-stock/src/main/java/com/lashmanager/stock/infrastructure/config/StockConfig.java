package com.lashmanager.stock.infrastructure.config;

import com.lashmanager.finance.domain.port.out.FinancialEntryRepository;
import com.lashmanager.stock.application.usecase.*;
import com.lashmanager.stock.domain.port.out.InventoryItemRepository;
import com.lashmanager.stock.domain.port.out.InventoryMovementRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StockConfig {

    @Bean
    public CreateInventoryItemUseCaseImpl createInventoryItemUseCase(InventoryItemRepository itemRepo) {
        return new CreateInventoryItemUseCaseImpl(itemRepo);
    }

    @Bean
    public UpdateInventoryItemUseCaseImpl updateInventoryItemUseCase(InventoryItemRepository itemRepo) {
        return new UpdateInventoryItemUseCaseImpl(itemRepo);
    }

    @Bean
    public GetInventoryItemUseCaseImpl getInventoryItemUseCase(InventoryItemRepository itemRepo) {
        return new GetInventoryItemUseCaseImpl(itemRepo);
    }

    @Bean
    public ListInventoryItemsUseCaseImpl listInventoryItemsUseCase(InventoryItemRepository itemRepo) {
        return new ListInventoryItemsUseCaseImpl(itemRepo);
    }

    @Bean
    public DeleteInventoryItemUseCaseImpl deleteInventoryItemUseCase(
            InventoryItemRepository itemRepo,
            InventoryMovementRepository movRepo) {
        return new DeleteInventoryItemUseCaseImpl(itemRepo, movRepo);
    }

    @Bean
    public DeactivateInventoryItemUseCaseImpl deactivateInventoryItemUseCase(InventoryItemRepository itemRepo) {
        return new DeactivateInventoryItemUseCaseImpl(itemRepo);
    }

    @Bean
    public RegisterPurchaseUseCaseImpl registerPurchaseUseCase(
            InventoryItemRepository itemRepo,
            InventoryMovementRepository movRepo,
            FinancialEntryRepository financialEntryRepository) {
        return new RegisterPurchaseUseCaseImpl(itemRepo, movRepo, financialEntryRepository);
    }

    @Bean
    public RegisterManualExitUseCaseImpl registerManualExitUseCase(
            InventoryItemRepository itemRepo,
            InventoryMovementRepository movRepo) {
        return new RegisterManualExitUseCaseImpl(itemRepo, movRepo);
    }

    @Bean
    public ListMovementsUseCaseImpl listMovementsUseCase(InventoryMovementRepository movRepo) {
        return new ListMovementsUseCaseImpl(movRepo);
    }
}
