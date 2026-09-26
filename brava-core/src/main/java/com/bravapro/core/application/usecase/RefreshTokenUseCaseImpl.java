package com.bravapro.core.application.usecase;

import com.bravapro.core.domain.exception.InvalidCredentialsException;
import com.bravapro.core.domain.exception.TokenExpiredException;
import com.bravapro.core.domain.exception.UserNotFoundException;
import com.bravapro.core.domain.model.User;
import com.bravapro.core.domain.port.in.RefreshTokenUseCase;
import com.bravapro.core.domain.port.out.TokenPort;
import com.bravapro.core.domain.port.out.UserRepository;
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
        User user = userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);

        if (!user.isActive()) {
            throw new InvalidCredentialsException();
        }

        String tenantId = user.getTenantId() != null ? user.getTenantId().toString() : null;
        String newAccessToken =
                tokenPort.generateAccessToken(user.getEmail(), user.getRole().name(), tenantId);
        return new RefreshResponse(newAccessToken);
    }
}
