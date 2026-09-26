package com.bravapro.settings.application.command;

import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Convida alguém pra assinatura já definindo o acesso: administrador (tudo) ou permissões por
 * módulo/ação ({@code permissions} = chaves como {@code "client"}, {@code "client.create"}).
 */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InviteUserCommand extends AbstractCommand {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 2, max = 255)
    private String name;

    @NotBlank(message = "E-mail é obrigatório")
    @Email(message = "E-mail inválido")
    private String email;

    private boolean admin;

    private boolean professional;

    private Set<String> permissions;
}
