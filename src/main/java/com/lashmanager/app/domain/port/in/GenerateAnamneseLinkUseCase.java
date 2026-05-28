package com.lashmanager.app.domain.port.in;

import java.util.UUID;

public interface GenerateAnamneseLinkUseCase {
    String execute(UUID clientId);
}
