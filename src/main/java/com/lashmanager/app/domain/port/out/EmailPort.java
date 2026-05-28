package com.lashmanager.app.domain.port.out;

public interface EmailPort {
    void sendPasswordResetEmail(String to, String name, String resetToken);
}
