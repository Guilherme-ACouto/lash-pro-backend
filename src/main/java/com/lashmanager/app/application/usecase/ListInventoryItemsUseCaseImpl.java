package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import com.lashmanager.app.domain.port.in.ListInventoryItemsUseCase;
import com.lashmanager.app.domain.port.out.InventoryItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListInventoryItemsUseCaseImpl implements ListInventoryItemsUseCase {

    private final InventoryItemRepository itemRepository;

    @Override
    public Page<InventoryItemResult> execute(ListInventoryItemsQuery query, Pageable pageable) {
        Boolean active = resolveActive(query.status());
        boolean onlyLowStock = "LOW_STOCK".equalsIgnoreCase(query.filter());
        return itemRepository.listWithFilters(query.search(), active, onlyLowStock, pageable)
                .map(InventoryUseCaseMapper::toItemResult);
    }

    private Boolean resolveActive(String status) {
        if ("ACTIVE".equalsIgnoreCase(status)) return true;
        if ("INACTIVE".equalsIgnoreCase(status)) return false;
        return null;
    }
}
