package com.lashmanager.core.application.command;

import com.lashmanager.core.domain.port.in.RegisterUseCase;
import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCommand extends AbstractCommand {

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha é obrigatória")
    @Size(min = 6, message = "Senha deve ter no mínimo 6 caracteres")
    private String password;

    public RegisterUseCase.RegisterData toDomainCommand() {
        return new RegisterUseCase.RegisterData(name, email, password);
    }
}
