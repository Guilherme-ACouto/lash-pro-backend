package com.lashmanager.stock.application.usecase;

import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import com.lashmanager.stock.domain.port.in.ListInventoryItemsUseCase;
import com.lashmanager.stock.domain.port.out.InventoryItemQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ListInventoryItemsUseCaseImpl implements ListInventoryItemsUseCase {

    private final InventoryItemQueryRepository itemQueryRepository;

    @Override
    public Page<CreateInventoryItemUseCase.InventoryItemResult> execute(
            ListInventoryItemsQuery query, Pageable pageable) {
        Boolean active = resolveActive(query.status());
        boolean onlyLowStock = "LOW_STOCK".equalsIgnoreCase(query.filter());
        String search = query.search() != null ? query.search() : "";
        return itemQueryRepository
                .listWithFilters(search, active, onlyLowStock, pageable)
                .map(InventoryUseCaseMapper::toItemResult);
    }

    private Boolean resolveActive(String status) {
        if ("ACTIVE".equalsIgnoreCase(status)) {
            return true;
        }
        return "INACTIVE".equalsIgnoreCase(status) ? false : null;
    }
}
