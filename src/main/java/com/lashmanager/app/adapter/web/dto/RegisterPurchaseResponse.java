package com.lashmanager.app.adapter.web.dto;

public record RegisterPurchaseResponse(
        InventoryItemResponse item,
        InventoryMovementResponse movement,
        String financialEntryId
) {}
