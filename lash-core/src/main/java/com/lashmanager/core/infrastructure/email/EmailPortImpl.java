package com.lashmanager.core.infrastructure.email;

import com.lashmanager.core.domain.port.out.EmailPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailPortImpl implements EmailPort {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username:noreply@lashmanager.com}")
    private String fromEmail;

    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private String frontendUrl;

    @Override
    public void sendPasswordResetEmail(String to, String name, String resetToken) {
        String resetLink = frontendUrl + "/auth/reset-password?token=" + resetToken;
        log.info("Link de recuperação de senha para {}: {}", to, resetLink);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Recuperação de senha - Lash Manager");
            message.setText(
                    "Olá, " + name + "!\n\n" +
                    "Recebemos uma solicitação para redefinir sua senha.\n\n" +
                    "Clique no link abaixo para criar uma nova senha (válido por 1 hora):\n" +
                    resetLink + "\n\n" +
                    "Se não foi você, ignore este email.\n\n" +
                    "Equipe Lash Manager"
            );
            mailSender.send(message);
        } catch (Exception e) {
            log.warn("Falha ao enviar email de recuperação para {}: {}", to, e.getMessage());
        }
    }
}
