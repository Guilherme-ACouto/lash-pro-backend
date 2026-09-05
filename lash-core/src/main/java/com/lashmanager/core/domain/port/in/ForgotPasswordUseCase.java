package com.lashmanager.core.domain.port.in;

public interface ForgotPasswordUseCase {
  void execute(String email);
}
