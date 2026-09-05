package com.lashmanager.clients.domain.port.out;

import com.lashmanager.clients.domain.model.Client;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Porta de leitura — separada de ClientRepository (escrita) conforme RBK-27. Reaproveita o mesmo
 * ClientJpaRepository/ClientMapper por baixo (Nível 2: separação lógica, não física — ver design.md
 * do refactor-backend).
 */
public interface ClientQueryRepository {
    Optional<Client> findById(UUID id);

    Page<Client> findAll(String search, Boolean active, Pageable pageable);
}
