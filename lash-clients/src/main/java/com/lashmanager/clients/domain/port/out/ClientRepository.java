package com.lashmanager.clients.domain.port.out;

import com.lashmanager.clients.domain.model.Client;
import java.util.Optional;
import java.util.UUID;

/**
 * Porta de escrita. Leitura (listagem/busca) foi separada para ClientQueryRepository (RBK-27) —
 * findById continua aqui também, porque os use cases de escrita (Update/Deactivate/Delete) precisam
 * carregar o agregado completo antes de mutar e salvar.
 */
public interface ClientRepository {
  Client save(Client client);

  Optional<Client> findById(UUID id);

  boolean existsByPhone(String phone);

  boolean existsByPhoneAndIdNot(String phone, UUID id);

  void deleteById(UUID id);
}
