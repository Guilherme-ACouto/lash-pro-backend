package com.lashmanager.core.application.command;

import com.lashmanager.core.infrastructure.command.AbstractCommand;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ActivateAccountCommand extends AbstractCommand {

  @NotBlank(message = "Chave de ativação é obrigatória")
  private String activationKey;
}
