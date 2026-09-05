package com.lashmanager.core.adapter.web.controller;

import com.lashmanager.core.adapter.web.dto.ForgotPasswordRequest;
import com.lashmanager.core.adapter.web.dto.LoginRequest;
import com.lashmanager.core.adapter.web.dto.LoginResponse;
import com.lashmanager.core.adapter.web.dto.RefreshTokenRequest;
import com.lashmanager.core.domain.port.in.ForgotPasswordUseCase;
import com.lashmanager.core.domain.port.in.LoginUseCase;
import com.lashmanager.core.domain.port.in.RefreshTokenUseCase;
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginUseCase.LoginResponse result =
                loginUseCase.execute(new LoginUseCase.LoginCommand(request.email(), request.password()));
        return ResponseEntity.ok(new LoginResponse(
                result.accessToken(), result.refreshToken(), result.name(), result.email(), result.role()));
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
}
