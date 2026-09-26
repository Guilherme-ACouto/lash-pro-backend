package com.bravapro.core.infrastructure.command;

import com.bravapro.core.domain.permission.Permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Permissão exigida pra executar um Command — igual ao {@code @CommandPermission} da Pontta (ex.:
 * {@code KanbanUpdateCommand}). Checada pelo {@link CommandInterceptor} antes do
 * {@code ApplicationService.when(...)}; administradora da assinatura passa direto.
 *
 * <p>{@code admin = true}: só administradora (rotinas de Configurações). Command sem esta anotação
 * não exige permissão fina (ex.: fluxos públicos por token, registro).
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CommandPermission {

    Permission[] value() default {};

    boolean admin() default false;
}
