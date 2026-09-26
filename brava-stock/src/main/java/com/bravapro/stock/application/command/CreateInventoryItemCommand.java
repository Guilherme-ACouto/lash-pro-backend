package com.bravapro.stock.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateInventoryItemCommand extends AbstractCommand {

    @NotBlank
    private final String name;

    private final String internalCode;

    @NotBlank
    private final String unit;

    @NotNull
    @DecimalMin("0.00")
    private final BigDecimal costPrice;

    private final String supplier;

    @NotNull
    @DecimalMin("0")
    private final BigDecimal currentQuantity;

    @NotNull
    @DecimalMin("0")
    private final BigDecimal minimumQuantity;

    private final String notes;
}
