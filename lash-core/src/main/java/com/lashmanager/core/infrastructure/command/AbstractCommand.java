package com.lashmanager.core.infrastructure.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Getter;

/**
 * Base de todo Command da aplicação. O CommandInterceptor intercepta qualquer
 * ApplicationService.when(AbstractCommand) por convenção de assinatura — não é preciso registrar
 * cada Command manualmente.
 */
@Getter
public abstract class AbstractCommand {

  @JsonIgnore private final UUID commandId = UUID.randomUUID();

  @JsonIgnore private final LocalDateTime issuedAt = LocalDateTime.now();
}
