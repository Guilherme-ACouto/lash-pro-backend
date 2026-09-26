package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.exception.PasswordResetTokenInvalidException;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.ResetPasswordUseCase;
import com.bravapro.core.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Consome o link do e-mail de "esqueci minha senha" (ou de "redefinir senha" disparado pela
 * administradora). Trocar a senha encerra as sessões abertas com a senha antiga.
 */
@Service
@RequiredArgsConstructor
public class ResetPasswordUseCaseImpl implements ResetPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void execute(String token, String newPassword) {
        User user = userRepository.findByPasswordResetToken(token).orElseThrow(PasswordResetTokenInvalidException::new);
        if (!user.isPasswordResetTokenValid()) {
            throw new PasswordResetTokenInvalidException();
        }
        user.changePassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
