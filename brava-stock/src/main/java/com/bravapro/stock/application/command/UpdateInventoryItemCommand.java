package com.bravapro.stock.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.CommandPermission;
import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code id} não vem no corpo do JSON — vem do {@code @PathVariable} da URL, setado via
 * {@link #id(UUID)} pelo Resource antes de repassar pra ApplicationService.
 */
@CommandPermission(Permission.INVENTORY_UPDATE)
@Getter
@AllArgsConstructor
public class UpdateInventoryItemCommand extends AbstractCommand {

    private UUID id;

    @NotBlank
    private final String name;

    @NotBlank
    private final String unit;

    @NotNull
    @DecimalMin("0.00")
    private final BigDecimal costPrice;

    private final String supplier;

    @NotNull
    @DecimalMin("0")
    private final BigDecimal minimumQuantity;

    private final String notes;

    public UpdateInventoryItemCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
