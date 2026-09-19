package com.lashmanager.stock.domain.port.in;

import com.lashmanager.stock.application.command.CreateInventoryItemCommand;
import com.lashmanager.stock.application.command.UpdateInventoryItemCommand;
import com.lashmanager.stock.domain.model.InventoryItem;

public interface InventoryItemUseCase {

    InventoryItem create(CreateInventoryItemCommand command);

    void update(InventoryItem item, UpdateInventoryItemCommand command);

    void delete(InventoryItem item);

    void deactivate(InventoryItem item);

    void reactivate(InventoryItem item);
}
