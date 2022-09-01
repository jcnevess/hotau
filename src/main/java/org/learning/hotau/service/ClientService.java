package org.learning.hotau.service;

import org.learning.hotau.dto.ClientForm;
import org.learning.hotau.model.Client;

import java.util.List;

public interface ClientService {
    Client create(ClientForm form);
    Client get(Long id);
    List<Client> getAll();
    Client update(ClientForm form);
    void delete(Long id);
}
