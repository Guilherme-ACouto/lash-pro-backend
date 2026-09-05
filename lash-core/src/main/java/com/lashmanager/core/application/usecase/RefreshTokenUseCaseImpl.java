package com.lashmanager.core.application.usecase;

import com.lashmanager.core.domain.exception.InvalidCredentialsException;
import com.lashmanager.core.domain.exception.TokenExpiredException;
import com.lashmanager.core.domain.exception.UserNotFoundException;
import com.lashmanager.core.domain.model.User;
import com.lashmanager.core.domain.port.in.RefreshTokenUseCase;
import com.lashmanager.core.domain.port.out.TokenPort;
import com.lashmanager.core.domain.port.out.UserRepository;
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
