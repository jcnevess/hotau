package org.learning.hotau.unit;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.ClientForm;
import org.learning.hotau.model.Address;
import org.learning.hotau.model.Client;
import org.learning.hotau.repository.ClientRepository;
import org.learning.hotau.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.mockito.Mockito.doReturn;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    @Test
    void testSave() {
        // Setup our mock repository
        Client client = new Client();
        client.setId(1L);
        client.setEmail("jose@dasilva.com");
        client.setFullName("José da Silva");

        Address address = new Address();
        address.setStreet("Sunflower St.");
        address.setStreetNumber("2100");
        address.setNeighborhood("Spring Upper Yard");
        address.setZipcode("99999-999");
        address.setCity("Springfield");
        address.setState("Old River");
        address.setCountry("Brazil");
        client.setAddress(address);

        client.setMainPhoneNumber("+5555123456789");
        client.setSecondaryPhoneNumber("+5555987654321");
        client.setCpfCode("07068093868");
        client.setNationalIdCode("1234567");
        client.setBirthday(LocalDate.of(1980, 2, 2));
        client.setClientSince(LocalDateTime.now());

        doReturn(client).when(clientRepository).save(any(Client.class));

        // Execute the service call
        ClientForm clientForm = new ClientForm();
        clientForm.setEmail("jose@dasilva.com");
        clientForm.setFullName("José da Silva");
        clientForm.setAddressStreet("Sunflower St.");
        clientForm.setAddressStreetNumber("2100");
        clientForm.setAddressNeighborhood("Spring Upper Yard");
        clientForm.setAddressZipcode("99999-999");
        clientForm.setAddressCity("Springfield");
        clientForm.setAddressState("Old River");
        clientForm.setAddressCountry("Brazil");
        clientForm.setMainPhoneNumber("+5555123456789");
        clientForm.setSecondaryPhoneNumber("+5555987654321");
        clientForm.setCpfCode("07068093868");
        clientForm.setNationalIdCode("1234567");
        clientForm.setBirthday(LocalDate.of(1980, 2, 2));

        Client returnedClient = clientService.create(clientForm);

        // Assert the response
        Assertions.assertNotNull(returnedClient);
        Assertions.assertNotNull(returnedClient.getId());
        Assertions.assertEquals("jose@dasilva.com", returnedClient.getEmail());
        Assertions.assertEquals("José da Silva", returnedClient.getFullName());
        Assertions.assertEquals("Sunflower St.", returnedClient.getAddress().getStreet());
        Assertions.assertEquals("2100", returnedClient.getAddress().getStreetNumber());
        Assertions.assertEquals("Spring Upper Yard", returnedClient.getAddress().getNeighborhood());
        Assertions.assertEquals("99999-999", returnedClient.getAddress().getZipcode());
        Assertions.assertEquals("Springfield", returnedClient.getAddress().getCity());
        Assertions.assertEquals("Old River", returnedClient.getAddress().getState());
        Assertions.assertEquals("Brazil", returnedClient.getAddress().getCountry());
        Assertions.assertEquals("+5555123456789", returnedClient.getMainPhoneNumber());
        Assertions.assertEquals("+5555987654321", returnedClient.getSecondaryPhoneNumber());
        Assertions.assertEquals("07068093868", returnedClient.getCpfCode());
        Assertions.assertEquals("1234567", returnedClient.getNationalIdCode());
        Assertions.assertEquals(LocalDate.of(1980, 2, 2), returnedClient.getBirthday());
        Assertions.assertTrue(
                returnedClient.getClientSince().until(LocalDateTime.now(), ChronoUnit.MINUTES) < 2,
                "Signup datetime should be at most 2 minutes before now");
        Assertions.assertTrue(returnedClient.getPets().isEmpty());

    }
}
