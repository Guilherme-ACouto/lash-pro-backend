package com.lashmanager.core.domain.port.out;

import com.lashmanager.core.domain.model.CommandAuditLog;
import java.util.Optional;

public interface CommandAuditLogRepository {
  CommandAuditLog save(CommandAuditLog commandAuditLog);

  boolean existsByCommandClass(String commandClass);

  Optional<CommandAuditLog> findLatestByCommandClass(String commandClass);
}
