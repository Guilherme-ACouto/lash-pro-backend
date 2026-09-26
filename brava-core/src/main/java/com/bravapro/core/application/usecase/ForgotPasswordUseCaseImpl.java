package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.ForgotPasswordUseCase;
import com.bravapro.core.domain.port.out.EmailPort;
import com.bravapro.core.domain.port.out.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ForgotPasswordUseCaseImpl implements ForgotPasswordUseCase {

    private final UserRepository userRepository;
    private final EmailPort emailPort;

    @Override
    public void execute(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) {
            log.info("Recuperação de senha para email não cadastrado: {}", email);
            return;
        }

        User user = userOpt.get();
        String token = UUID.randomUUID().toString();

        user.requestPasswordReset(token, LocalDateTime.now().plusHours(1));
        userRepository.save(user);
        emailPort.sendPasswordResetEmail(user.getEmail(), user.getName(), token);
        log.info("Email de recuperação enviado para: {}", email);
    }
}
