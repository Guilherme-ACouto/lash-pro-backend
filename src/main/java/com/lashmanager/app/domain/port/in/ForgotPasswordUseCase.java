package com.lashmanager.app.domain.port.in;

public interface ForgotPasswordUseCase {
    void execute(String email);
}
