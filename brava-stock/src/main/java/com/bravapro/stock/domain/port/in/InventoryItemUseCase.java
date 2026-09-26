package com.bravapro.stock.domain.port.in;

import com.bravapro.stock.application.command.CreateInventoryItemCommand;
import com.bravapro.stock.application.command.UpdateInventoryItemCommand;
import com.bravapro.stock.domain.model.InventoryItem;

public interface InventoryItemUseCase {

    InventoryItem create(CreateInventoryItemCommand command);

    void update(InventoryItem item, UpdateInventoryItemCommand command);

    void delete(InventoryItem item);

    void deactivate(InventoryItem item);

    void reactivate(InventoryItem item);
}
