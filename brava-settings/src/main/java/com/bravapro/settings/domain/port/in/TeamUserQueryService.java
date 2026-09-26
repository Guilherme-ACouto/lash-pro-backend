package com.bravapro.settings.domain.port.in;

import com.bravapro.settings.domain.model.TeamUser;

import java.util.List;
import java.util.UUID;

public interface TeamUserQueryService {

    List<TeamUser> list();

    TeamUser getById(UUID id);
}
