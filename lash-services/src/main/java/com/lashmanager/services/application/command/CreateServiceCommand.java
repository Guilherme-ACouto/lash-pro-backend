package com.lashmanager.services.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateServiceCommand extends AbstractCommand {

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
}
