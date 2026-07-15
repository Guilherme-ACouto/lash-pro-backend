package com.lashmanager.clients.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import com.lashmanager.clients.domain.port.in.CreateClientUseCase;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClientCommand extends AbstractCommand {

    @NotBlank @Size(min = 2, max = 100)
    private String name;

    @NotBlank @Size(min = 10, max = 20)
    private String phone;

    private String email;
    private LocalDate birthDate;

    @Size(max = 500)
    private String notes;

    public CreateClientUseCase.CreateClientCommand toDomainCommand() {
        return new CreateClientUseCase.CreateClientCommand(name, phone, email, birthDate, notes);
    }
}
