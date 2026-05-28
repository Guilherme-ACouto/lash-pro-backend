package com.lashmanager.app.domain.port.out;

import com.lashmanager.app.domain.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ClientRepository {
    Client save(Client client);
    Optional<Client> findById(UUID id);
    Page<Client> findAll(String search, Boolean active, Pageable pageable);
    boolean existsByPhone(String phone);
    boolean existsByPhoneAndIdNot(String phone, UUID id);
    void deleteById(UUID id);
}
