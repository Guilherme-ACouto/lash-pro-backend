package com.lashmanager.core.domain.port.in;

import java.util.UUID;

public interface RegisterUseCase {

  record RegisterData(String name, String email, String password) {}

  record RegisterResult(UUID userId, String email) {}

  RegisterResult execute(RegisterData data);
}
