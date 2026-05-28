package com.lashmanager.app.domain.port.in;

import com.lashmanager.app.domain.port.in.CreateInventoryItemUseCase.InventoryItemResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListInventoryItemsUseCase {

    record ListInventoryItemsQuery(
            String search,
            String status,
            String filter
    ) {}

    Page<InventoryItemResult> execute(ListInventoryItemsQuery query, Pageable pageable);
}
