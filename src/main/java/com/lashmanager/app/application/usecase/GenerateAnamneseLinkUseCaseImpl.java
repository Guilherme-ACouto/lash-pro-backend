package com.lashmanager.app.application.usecase;

import com.lashmanager.app.domain.model.AnamneseToken;
import com.lashmanager.app.domain.port.in.GenerateAnamneseLinkUseCase;
import com.lashmanager.app.domain.port.out.AnamneseTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service @RequiredArgsConstructor
public class GenerateAnamneseLinkUseCaseImpl implements GenerateAnamneseLinkUseCase {
    private final AnamneseTokenRepository tokenRepository;

    @Value("${app.frontend-url:http://localhost:4200}")
    private String frontendUrl;

    @Override public String execute(UUID clientId) {
        String token = UUID.randomUUID().toString().replace("-", "") + UUID.randomUUID().toString().replace("-", "");
        token = token.substring(0, 64);
        AnamneseToken tokenEntity = AnamneseToken.builder()
            .clientId(clientId).token(token)
            .expiresAt(LocalDateTime.now().plusDays(7))
            .used(false).build();
        tokenRepository.save(tokenEntity);
        return frontendUrl + "/ficha/" + token;
    }
}
