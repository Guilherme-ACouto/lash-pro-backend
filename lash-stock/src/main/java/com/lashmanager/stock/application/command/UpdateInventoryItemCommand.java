package com.lashmanager.stock.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.stock.domain.port.in.UpdateInventoryItemUseCase;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInventoryItemCommand extends AbstractCommand {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String unit;

    @NotNull @DecimalMin("0.00")
    private BigDecimal costPrice;

    private String supplier;

    @NotNull @DecimalMin("0")
    private BigDecimal minimumQuantity;

    private String notes;

    public UpdateInventoryItemUseCase.UpdateInventoryItemCommand toDomainCommand() {
        return new UpdateInventoryItemUseCase.UpdateInventoryItemCommand(
                id, name, unit, costPrice, supplier, minimumQuantity, notes);
    }
}
