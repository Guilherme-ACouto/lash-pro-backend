package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.ResendActivationUseCase;
import com.bravapro.core.domain.port.out.EmailPort;
import com.bravapro.core.domain.port.out.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ResendActivationUseCaseImpl implements ResendActivationUseCase {

    private final UserRepository userRepository;
    private final EmailPort emailPort;

    @Value("${app.activation.key-expiration-hours:48}")
    private long activationKeyExpirationHours;

    @Override
    public void execute(String email) {
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty() || userOpt.get().isActive()) {
            log.info("Reenvio de ativação ignorado (e-mail inexistente ou já ativo): {}", email);
            return;
        }

        User user = userOpt.get();
        String activationKey = UUID.randomUUID().toString();

        User updated = user.toBuilder()
                .active(false)
                .activationKey(activationKey)
                .activationKeyExpiry(LocalDateTime.now().plusHours(activationKeyExpirationHours))
                .updatedAt(LocalDateTime.now())
                .build();

        userRepository.save(updated);
        emailPort.sendActivationEmail(user.getEmail(), user.getName(), activationKey);
        log.info("Ativação reenviada para: {}", email);
    }
}
