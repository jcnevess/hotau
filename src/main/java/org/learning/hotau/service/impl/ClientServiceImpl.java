package org.learning.hotau.service.impl;

import org.learning.hotau.dto.form.ClientForm;
import org.learning.hotau.model.Address;
import org.learning.hotau.model.Client;
import org.learning.hotau.repository.ClientRepository;
import org.learning.hotau.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientServiceImpl implements ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public Client save(ClientForm form) {
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
    public Client findById(Long id) {
        return clientRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Client update(Long id, ClientForm form) {
        return clientRepository.findById(id)
                .map(client -> {
                    client.setEmail(form.getEmail());
                    client.setFullName(form.getFullName());

                    Address address = client.getAddress();
                    address.setStreet(form.getAddressStreet());
                    address.setStreetNumber(form.getAddressStreetNumber());
                    address.setNeighborhood(form.getAddressNeighborhood());
                    address.setZipcode(form.getAddressZipcode());
                    address.setCity(form.getAddressCity());
                    address.setState(form.getAddressState());
                    address.setCountry(form.getAddressCountry());

                    client.setMainPhoneNumber(form.getMainPhoneNumber());
                    client.setSecondaryPhoneNumber(form.getSecondaryPhoneNumber());
                    client.setCpfCode(form.getCpfCode());
                    client.setNationalIdCode(form.getNationalIdCode());
                    client.setBirthday(form.getBirthday());

                    return clientRepository.save(client);
                })
                .orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        clientRepository.findById(id).ifPresentOrElse(
                client -> clientRepository.deleteById(id),
                () -> { throw new NoSuchElementException(); }
        );
    }

    @Override
    public List<Client> filterByCpfCode(String cpfCode) {
        return clientRepository.findAllByCpfCode(cpfCode);
    }
}
