package com.lashmanager.stock.domain.port.in;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListInventoryItemsUseCase {

  record ListInventoryItemsQuery(String search, String status, String filter) {}

  Page<CreateInventoryItemUseCase.InventoryItemResult> execute(
      ListInventoryItemsQuery query, Pageable pageable);
}
