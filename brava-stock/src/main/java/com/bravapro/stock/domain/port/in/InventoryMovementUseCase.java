package com.bravapro.stock.domain.port.in;

import com.bravapro.stock.application.command.RegisterManualExitCommand;
import com.bravapro.stock.application.command.RegisterPurchaseCommand;
import com.bravapro.stock.domain.model.InventoryItem;
import com.bravapro.stock.domain.model.InventoryMovement;

/**
 * Agregado separado de {@link InventoryItemUseCase} — mesmo as operações aqui também mutando o
 * {@code InventoryItem} (efeito colateral: atualizar a quantidade), o alvo primário é o
 * {@code InventoryMovement} sendo criado (é ele que é o resultado da operação; ver design.md da
 * issue #7). {@code ApplicationService} busca o {@code InventoryItem} e repassa já carregado.
 */
public interface InventoryMovementUseCase {

    InventoryMovement registerPurchase(InventoryItem item, RegisterPurchaseCommand command);

    InventoryMovement registerManualExit(InventoryItem item, RegisterManualExitCommand command);
}
