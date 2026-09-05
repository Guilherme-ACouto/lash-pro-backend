package com.lashmanager.core.domain.port.in;

public interface LoginUseCase {

  record LoginCommand(String email, String password) {}

  record LoginResponse(
      String accessToken, String refreshToken, String name, String email, String role) {}

  LoginResponse execute(LoginCommand command);
}
