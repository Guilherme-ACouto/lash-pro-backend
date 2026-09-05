package com.lashmanager.core.adapter.web.controller;

import com.lashmanager.core.adapter.web.dto.RegisterRequest;
import com.lashmanager.core.adapter.web.dto.RegisterResponse;
import com.lashmanager.core.adapter.web.dto.ResendActivationRequest;
import com.lashmanager.core.application.command.RegisterCommand;
import com.lashmanager.core.application.command.ResendActivationCommand;
import com.lashmanager.core.application.service.RegisterApplicationService;
import com.lashmanager.core.application.service.ResendActivationApplicationService;
import com.lashmanager.core.domain.port.in.RegisterUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegisterController {

    private final RegisterApplicationService registerApplicationService;
    private final ResendActivationApplicationService resendActivationApplicationService;

    @PostMapping
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest request) {
        RegisterUseCase.RegisterResult result = registerApplicationService.when(
                new RegisterCommand(request.name(), request.email(), request.password()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(result.userId(), result.email()));
    }

    @PostMapping("/resend")
    public ResponseEntity<Void> resend(@Valid @RequestBody ResendActivationRequest request) {
        resendActivationApplicationService.when(new ResendActivationCommand(request.email()));
        return ResponseEntity.noContent().build();
    }
}
