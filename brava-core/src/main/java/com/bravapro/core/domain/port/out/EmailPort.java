package com.bravapro.core.domain.port.out;

public interface EmailPort {
    void sendPasswordResetEmail(String to, String name, String resetToken);

    void sendActivationEmail(String to, String name, String activationKey);

    void sendInviteEmail(String to, String name, String tenantName, String inviteToken, long validHours);
}
