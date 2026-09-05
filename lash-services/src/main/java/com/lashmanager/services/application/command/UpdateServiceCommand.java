package com.lashmanager.services.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * O {@code id} não vem no corpo do JSON — vem do {@code @PathVariable} da URL, setado via
 * {@link #id(UUID)} pelo Resource antes de repassar pra ApplicationService (ver ClientResource
 * como referência do mesmo padrão).
 */
@Getter
@AllArgsConstructor
public class UpdateServiceCommand extends AbstractCommand {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 100)
    private final String name;

    @Size(max = 500)
    private final String description;

    @NotNull
    @DecimalMin("0.01")
    private final BigDecimal price;

    @Min(1)
    private final int durationMinutes;

    public UpdateServiceCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
