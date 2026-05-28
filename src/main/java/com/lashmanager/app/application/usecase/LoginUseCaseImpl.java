package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InvalidCredentialsException;
import com.lashmanager.app.domain.model.User;
import com.lashmanager.app.domain.port.in.LoginUseCase;
import com.lashmanager.app.domain.port.out.TokenPort;
import com.lashmanager.app.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenPort tokenPort;

    @Override
    public LoginResponse execute(LoginCommand command) {
        User user = userRepository.findByEmail(command.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!user.isActive()) {
            throw new InvalidCredentialsException();
        }

        if (!passwordEncoder.matches(command.password(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String accessToken = tokenPort.generateAccessToken(user.getEmail(), user.getRole().name());
        String refreshToken = tokenPort.generateRefreshToken(user.getEmail());

        log.info("Login realizado: {}", user.getEmail());
        return new LoginResponse(accessToken, refreshToken, user.getName(), user.getEmail(), user.getRole().name());
    }
}
