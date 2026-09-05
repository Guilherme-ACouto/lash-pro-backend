package com.lashmanager.core.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResendActivationCommand extends AbstractCommand {

  @NotBlank(message = "Email é obrigatório")
  @Email(message = "Email inválido")
  private String email;
}
