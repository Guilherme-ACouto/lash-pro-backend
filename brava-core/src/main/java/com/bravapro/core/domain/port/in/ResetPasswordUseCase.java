package com.bravapro.core.domain.port.in;

public interface ResetPasswordUseCase {
    void execute(String token, String newPassword);
}
