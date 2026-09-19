package com.lashmanager.clients.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateClientCommand extends AbstractCommand {

    @NotBlank
    @Size(min = 2, max = 100)
    private final String name;

    @NotBlank
    @Size(min = 10, max = 20)
    private final String phone;

    private final String email;
    private final LocalDate birthDate;

    @Size(max = 500)
    private final String notes;
}
