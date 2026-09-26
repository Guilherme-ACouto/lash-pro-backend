package com.bravapro.stock.application.command;

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
 * ({@code /api/inventory/items/{id}/exit}), setado via {@link #itemId(UUID)}.
 */
@Getter
@AllArgsConstructor
public class RegisterManualExitCommand extends AbstractCommand {

    private UUID itemId;

    @NotNull
    @DecimalMin("0.001")
    private final BigDecimal quantity;

    @NotBlank
    private final String reason;

    private final String notes;

    @NotNull
    private final LocalDate exitDate;

    public RegisterManualExitCommand itemId(UUID itemId) {
        this.itemId = itemId;
        return this;
    }
}
