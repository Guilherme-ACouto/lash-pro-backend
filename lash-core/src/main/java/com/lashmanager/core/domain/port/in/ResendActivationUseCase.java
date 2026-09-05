package com.lashmanager.core.domain.port.in;

public interface ResendActivationUseCase {
    /**
     * Reenvia o e-mail de ativação para um cadastro pendente. Não revela se o e-mail existe ou já
     * está ativo (resposta sempre "genérica" do ponto de vista do chamador) — só reenvia de fato
     * quando encontra um usuário inativo com aquele e-mail.
     */
    void execute(String email);
}
