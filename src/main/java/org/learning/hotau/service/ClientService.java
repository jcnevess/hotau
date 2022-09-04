package org.learning.hotau.service;

import org.learning.hotau.dto.form.ClientForm;
import org.learning.hotau.model.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    Client save(ClientForm form);
    Client findById(Long id);
    List<Client> findAll();
    Client update(Long id, ClientForm form);
    void delete(Long id);
}
