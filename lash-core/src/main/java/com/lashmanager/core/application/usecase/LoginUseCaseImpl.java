package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.exception.AccountNotActivatedException;
import com.lashmanager.core.domain.exception.InvalidCredentialsException;
import com.lashmanager.core.domain.exception.TenantInactiveException;
import com.lashmanager.core.domain.model.Tenant;
import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.port.in.LoginUseCase;
import com.lashmanager.core.domain.port.out.TenantRepository;
import com.lashmanager.core.domain.port.out.TokenPort;
import com.lashmanager.core.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final TenantRepository tenantRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenPort tokenPort;

    @Override
    public LoginResponse execute(LoginCommand command) {
        User user = userRepository.findByEmail(command.email()).orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        if (!user.isActive()) {
            throw new AccountNotActivatedException();
        }

        if (user.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(user.getTenantId()).orElse(null);
            if (tenant != null && !tenant.isActive()) {
                throw new TenantInactiveException();
            }
        }

        String tenantId = user.getTenantId() != null ? user.getTenantId().toString() : null;
        String accessToken =
                tokenPort.generateAccessToken(user.getEmail(), user.getRole().name(), tenantId);
        String refreshToken = tokenPort.generateRefreshToken(user.getEmail());

        if (log.isInfoEnabled()) {
            log.info("Login realizado: {}", user.getEmail());
        }
        return new LoginResponse(
                accessToken,
                refreshToken,
                user.getName(),
                user.getEmail(),
                user.getRole().name());
    }
}
