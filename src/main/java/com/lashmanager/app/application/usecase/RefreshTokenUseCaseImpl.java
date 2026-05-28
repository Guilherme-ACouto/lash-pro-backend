package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.exception.InvalidCredentialsException;
import com.lashmanager.app.domain.exception.TokenExpiredException;
import com.lashmanager.app.domain.exception.UserNotFoundException;
import com.lashmanager.app.domain.model.User;
import com.lashmanager.app.domain.port.in.RefreshTokenUseCase;
import com.lashmanager.app.domain.port.out.TokenPort;
import com.lashmanager.app.domain.port.out.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefreshTokenUseCaseImpl implements RefreshTokenUseCase {

    private final TokenPort tokenPort;
    private final UserRepository userRepository;

    @Override
    public RefreshResponse execute(String refreshToken) {
        if (!tokenPort.isRefreshTokenValid(refreshToken)) {
            throw new TokenExpiredException();
        }

        String email = tokenPort.extractEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);

        if (!user.isActive()) {
            throw new InvalidCredentialsException();
        }

        String newAccessToken = tokenPort.generateAccessToken(user.getEmail(), user.getRole().name());
        return new RefreshResponse(newAccessToken);
    }
}
