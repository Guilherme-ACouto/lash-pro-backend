package com.lashmanager.core.infrastructure.web;

/**
 * Nomes dos headers de alerta usados pelo {@code RestUtils} — equivalente aos
 * {@code X-pontta-alert}/{@code X-pontta-params} do padrão de referência (Pontta), renomeados
 * pro produto.
 */
public final class WebHeaderConstants {

    public static final String X_LASH_ALERT = "X-lash-alert";
    public static final String X_LASH_PARAMS = "X-lash-params";

    private WebHeaderConstants() {}
}
