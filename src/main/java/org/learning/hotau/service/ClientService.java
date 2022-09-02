package org.learning.hotau.service;

import org.learning.hotau.dto.form.ClientForm;
import org.learning.hotau.model.Client;

import java.util.List;

public interface ClientService {
    Client save(ClientForm form);
    Client findById(Long id);
    List<Client> findAll();
    Client update(ClientForm form);
    void delete(Long id);
}
