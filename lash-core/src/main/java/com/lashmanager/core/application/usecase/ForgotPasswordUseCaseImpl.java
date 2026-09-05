package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.port.in.ForgotPasswordUseCase;
import com.lashmanager.core.domain.port.out.EmailPort;
import com.lashmanager.core.domain.port.out.UserRepository;
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

        User updated = User.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getRole())
                .active(user.isActive())
                .passwordResetToken(token)
                .passwordResetTokenExpiry(LocalDateTime.now().plusHours(1))
                .createdAt(user.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(updated);
        emailPort.sendPasswordResetEmail(user.getEmail(), user.getName(), token);
        log.info("Email de recuperação enviado para: {}", email);
    }
}
