package com.lashmanager.services.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.services.domain.port.in.CreateServiceUseCase;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateServiceCommand extends AbstractCommand {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @Size(max = 500)
    private String description;

    @NotNull
    @DecimalMin("0.01")
    private BigDecimal price;

    @Min(1)
    private int durationMinutes;

    public CreateServiceUseCase.CreateServiceCommand toDomainCommand() {
        return new CreateServiceUseCase.CreateServiceCommand(name, description, price, durationMinutes);
    }
}
