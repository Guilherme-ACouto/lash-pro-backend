package com.bravapro.core.domain.model;

/** Tela pública de aceite: o que a pessoa precisa ver antes de criar a senha. */
public record InvitationDetails(String name, String email, String tenantName, boolean expired) {}
