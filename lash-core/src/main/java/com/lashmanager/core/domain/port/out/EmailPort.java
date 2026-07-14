package com.lashmanager.core.domain.port.out;

public interface EmailPort {
    void sendPasswordResetEmail(String to, String name, String resetToken);
}
