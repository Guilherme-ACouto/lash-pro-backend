package com.bravapro.stock.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code itemId} não vem no corpo do JSON — vem do {@code @PathVariable} da URL
 * ({@code /api/inventory/items/{id}/purchase}), setado via {@link #itemId(UUID)}.
 */
@CommandPermission(Permission.INVENTORY_UPDATE)
@Getter
@AllArgsConstructor
public class RegisterPurchaseCommand extends AbstractCommand {

    private UUID itemId;

    @NotNull
    @DecimalMin("0.001")
    private final BigDecimal quantity;

    @NotNull
    @DecimalMin("0.00")
    private final BigDecimal unitCost;

    private final String supplier;

    @NotNull
    private final LocalDate purchaseDate;

    @NotBlank
    private final String paymentType;

    private final LocalDate dueDate;
    private final String notes;

    public RegisterPurchaseCommand itemId(UUID itemId) {
        this.itemId = itemId;
        return this;
    }
}
