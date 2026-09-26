package com.bravapro.fichas.domain.port.in;

import com.bravapro.fichas.domain.model.AnamnesePublicView;

public interface PublicAnamneseQueryService {

    /** Lança {@code AnamneseLinkInvalidException}/{@code AnamneseLinkExpiredException}. */
    AnamnesePublicView getByToken(String token);
}
