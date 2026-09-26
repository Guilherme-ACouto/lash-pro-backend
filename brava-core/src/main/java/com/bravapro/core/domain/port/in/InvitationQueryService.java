package com.bravapro.core.domain.port.in;

import com.bravapro.core.domain.model.InvitationDetails;

public interface InvitationQueryService {

    InvitationDetails getByToken(String token);
}
