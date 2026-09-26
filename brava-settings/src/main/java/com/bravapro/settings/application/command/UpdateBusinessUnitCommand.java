package com.bravapro.settings.application.command;

import com.bravapro.core.domain.permission.Permission;
import com.bravapro.core.infrastructure.command.AbstractCommand;
import com.bravapro.core.infrastructure.command.CommandPermission;

import java.util.UUID;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** O {@code id} vem da URL (ver {@link #id(UUID)}), como nos outros Update*Command. */
@CommandPermission(admin = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBusinessUnitCommand extends AbstractCommand {

    private UUID id;

    @NotBlank(message = "Nome fantasia é obrigatório")
    @Size(max = 255)
    private String tradeName;

    @Size(max = 255)
    private String legalName;

    /** CPF ou CNPJ (ou vazio, se o documento não for informado). */
    private String documentType;

    @Size(max = 20)
    private String document;

    @Size(max = 50)
    private String municipalRegistration;

    @Size(max = 20)
    private String phone;

    @Size(max = 20)
    private String whatsapp;

    @Email(message = "E-mail inválido")
    @Size(max = 255)
    private String email;

    @Size(max = 100)
    private String instagram;

    @Size(max = 255)
    private String website;

    @Size(max = 9)
    private String zipCode;

    @Size(max = 255)
    private String street;

    @Size(max = 20)
    private String number;

    @Size(max = 100)
    private String complement;

    @Size(max = 100)
    private String district;

    @Size(max = 100)
    private String city;

    @Size(max = 2, message = "UF deve ter 2 letras")
    private String state;

    public UpdateBusinessUnitCommand id(UUID id) {
        this.id = id;
        return this;
    }
}
