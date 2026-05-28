package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.AnamneseToken;
import java.util.Optional;

public interface AnamneseTokenRepository {
    AnamneseToken save(AnamneseToken token);
    Optional<AnamneseToken> findByToken(String token);
    void deleteExpired();
}
