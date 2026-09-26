package com.bravapro.core.adapter.web.controller;

import com.bravapro.core.adapter.web.dto.ForgotPasswordRequest;
import com.bravapro.core.adapter.web.dto.LoginRequest;
import com.bravapro.core.adapter.web.dto.LoginResponse;
import com.bravapro.core.adapter.web.dto.RefreshTokenRequest;
import com.bravapro.core.adapter.web.dto.ResetPasswordRequest;
import com.bravapro.core.domain.port.in.ForgotPasswordUseCase;
import com.bravapro.core.domain.port.in.LoginUseCase;
import com.bravapro.core.domain.port.in.RefreshTokenUseCase;
import com.bravapro.core.domain.port.in.ResetPasswordUseCase;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final ForgotPasswordUseCase forgotPasswordUseCase;
    private final RefreshTokenUseCase refreshTokenUseCase;
    private final ResetPasswordUseCase resetPasswordUseCase;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginUseCase.LoginResponse result =
                loginUseCase.execute(new LoginUseCase.LoginCommand(request.email(), request.password()));
        return ResponseEntity.ok(new LoginResponse(
                result.accessToken(), result.refreshToken(), result.name(), result.email(), result.admin()));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        forgotPasswordUseCase.execute(request.email());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refresh(@Valid @RequestBody RefreshTokenRequest request) {
        RefreshTokenUseCase.RefreshResponse result = refreshTokenUseCase.execute(request.refreshToken());
        return ResponseEntity.ok(Map.of("accessToken", result.accessToken()));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        resetPasswordUseCase.execute(request.token(), request.password());
        return ResponseEntity.noContent().build();
    }
}
