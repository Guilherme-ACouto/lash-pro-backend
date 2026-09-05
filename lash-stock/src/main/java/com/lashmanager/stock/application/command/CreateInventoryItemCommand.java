package com.lashmanager.stock.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.stock.domain.port.in.CreateInventoryItemUseCase;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateInventoryItemCommand extends AbstractCommand {

    @NotBlank
    private String name;

    private String internalCode;

    @NotBlank
    private String unit;

    @NotNull
    @DecimalMin("0.00")
    private BigDecimal costPrice;

    private String supplier;

    @NotNull
    @DecimalMin("0")
    private BigDecimal currentQuantity;

    @NotNull
    @DecimalMin("0")
    private BigDecimal minimumQuantity;

    private String notes;

    public CreateInventoryItemUseCase.CreateInventoryItemCommand toDomainCommand() {
        return new CreateInventoryItemUseCase.CreateInventoryItemCommand(
                name, internalCode, unit, costPrice, supplier, currentQuantity, minimumQuantity, notes);
    }
}
