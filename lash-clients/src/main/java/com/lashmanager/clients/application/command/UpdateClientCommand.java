package com.lashmanager.clients.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.clients.domain.port.in.UpdateClientUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateClientCommand extends AbstractCommand {

    @NotNull
    private UUID id;

    @NotBlank @Size(min = 2, max = 100)
    private String name;

    @NotBlank @Size(min = 10, max = 20)
    private String phone;

    private String email;
    private LocalDate birthDate;

    @Size(max = 500)
    private String notes;

    public UpdateClientUseCase.UpdateClientCommand toDomainCommand() {
        return new UpdateClientUseCase.UpdateClientCommand(name, phone, email, birthDate, notes);
    }
}
