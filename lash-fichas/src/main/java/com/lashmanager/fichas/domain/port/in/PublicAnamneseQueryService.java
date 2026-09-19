package com.lashmanager.fichas.domain.port.in;

import com.lashmanager.fichas.domain.model.AnamnesePublicView;

public interface PublicAnamneseQueryService {

    /** Lança {@code AnamneseLinkInvalidException}/{@code AnamneseLinkExpiredException}. */
    AnamnesePublicView getByToken(String token);
}
