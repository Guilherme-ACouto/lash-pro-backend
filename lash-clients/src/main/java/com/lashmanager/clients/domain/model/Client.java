package com.lashmanager.clients.domain.model;

import com.lashmanager.clients.application.command.UpdateClientCommand;
import com.lashmanager.core.domain.model.DomainEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Client implements DomainEntity {
    private UUID id;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
    private String notes;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public void update(UpdateClientCommand command) {
        this.name = command.getName();
        this.phone = command.getPhone();
        this.email = command.getEmail();
        this.birthDate = command.getBirthDate();
        this.notes = command.getNotes();
        this.updatedAt = LocalDateTime.now();
    }

    public void deactivate() {
        this.active = false;
        this.updatedAt = LocalDateTime.now();
    }

    public void reactivate() {
        this.active = true;
        this.updatedAt = LocalDateTime.now();
    }
}
