package com.bravapro.core.infrastructure.email;

import com.bravapro.core.domain.port.out.EmailPort;
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

    @Value("${spring.mail.username:noreply@bravapro.com.br}")
    private String fromEmail;

    @Value("${app.cors.allowed-origins:http://localhost:4200}")
    private String frontendUrl;

    @Override
    public void sendPasswordResetEmail(String to, String name, String resetToken) {
        String resetLink = frontendBaseUrl() + "/auth/reset-password?token=" + resetToken;
        log.info("Link de recuperação de senha para {}: {}", to, resetLink);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Recuperação de senha - Brava Pro");
            message.setText("Olá, "
                    + name
                    + "!\n\n"
                    + "Recebemos uma solicitação para redefinir sua senha.\n\n"
                    + "Clique no link abaixo para criar uma nova senha (válido por 1 hora):\n"
                    + resetLink
                    + "\n\n"
                    + "Se não foi você, ignore este email.\n\n"
                    + "Equipe Brava Pro");
            mailSender.send(message);
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Falha ao enviar email de recuperação para {}: {}", to, e.getMessage());
            }
        }
    }

    @Override
    public void sendActivationEmail(String to, String name, String activationKey) {
        String activationLink = frontendBaseUrl() + "/auth/activation?key=" + activationKey;
        log.info("Link de ativação de conta para {}: {}", to, activationLink);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Confirme seu cadastro - Brava Pro");
            message.setText("Seja bem-vindo, "
                    + name
                    + "!\n\n"
                    + "Agora só precisamos que você confirme seu cadastro.\n\n"
                    + "Clique no link abaixo para ativar sua conta:\n"
                    + activationLink
                    + "\n\n"
                    + "Se não foi você, ignore este email.\n\n"
                    + "Equipe Brava Pro");
            mailSender.send(message);
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Falha ao enviar email de ativação para {}: {}", to, e.getMessage());
            }
        }
    }

    @Override
    public void sendInviteEmail(String to, String name, String tenantName, String inviteToken, long validHours) {
        String inviteLink = frontendBaseUrl() + "/auth/convite?token=" + inviteToken;
        log.info("Link de convite para {} ({}): {}", to, tenantName, inviteLink);

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setSubject("Convite para acessar o Brava Pro — " + tenantName);
            message.setText("Olá, "
                    + name
                    + "!\n\n"
                    + tenantName
                    + " convidou você para acessar o Brava Pro.\n\n"
                    + "Clique no link abaixo para criar sua senha e entrar (válido por "
                    + validHours
                    + " horas):\n"
                    + inviteLink
                    + "\n\n"
                    + "Se você não esperava este convite, ignore este email.\n\n"
                    + "Equipe Brava Pro");
            mailSender.send(message);
        } catch (Exception e) {
            if (log.isWarnEnabled()) {
                log.warn("Falha ao enviar email de convite para {}: {}", to, e.getMessage());
            }
        }
    }

    /** {@code app.cors.allowed-origins} pode ter várias origens separadas por vírgula — o link usa a primeira. */
    private String frontendBaseUrl() {
        return frontendUrl.split(",")[0].trim();
    }
}
