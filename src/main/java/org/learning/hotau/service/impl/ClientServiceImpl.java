package org.learning.hotau.service.impl;

import org.learning.hotau.dto.ClientForm;
import org.learning.hotau.model.Address;
import org.learning.hotau.model.Client;
import org.learning.hotau.repository.ClientRepository;
import org.learning.hotau.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client create(ClientForm form) {
        Client client = new Client();
        client.setEmail(form.getEmail());
        client.setFullName(form.getFullName());

        Address address = new Address();
        address.setStreet(form.getAddressStreet());
        address.setStreetNumber(form.getAddressStreetNumber());
        address.setNeighborhood(form.getAddressNeighborhood());
        address.setZipcode(form.getAddressZipcode());
        address.setCity(form.getAddressCity());
        address.setState(form.getAddressState());
        address.setCountry(form.getAddressCountry());
        client.setAddress(address);

        client.setMainPhoneNumber(form.getMainPhoneNumber());
        client.setSecondaryPhoneNumber(form.getSecondaryPhoneNumber());
        client.setCpfCode(form.getCpfCode());
        client.setNationalIdCode(form.getNationalIdCode());
        client.setBirthday(form.getBirthday());

        return clientRepository.save(client);
    }

    @Override
    public Client get(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public Client update(ClientForm form) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
