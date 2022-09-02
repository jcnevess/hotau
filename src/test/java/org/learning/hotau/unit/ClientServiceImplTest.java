package org.learning.hotau.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.ClientForm;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ClientServiceImplTest {

    private static final String MOCK_EMAIL_1 = "jose@dasilva.com";
    private static final String MOCK_FULL_NAME_1 = "José da Silva";
    private static final String MOCK_ADDRESS_STREET_1 = "Sunflower St.";
    private static final String MOCK_STREET_NUMBER_1 = "2100";
    private static final String MOCK_NEIGHBORHOOD_1 = "Spring Upper Yard";
    private static final String MOCK_ZIPCODE_1 = "99999-999";
    private static final String MOCK_CITY_1 = "Springfield";
    private static final String MOCK_STATE_1 = "Old River";
    private static final String MOCK_COUNTRY_1 = "Brazil";
    private static final String MOCK_MAIN_PHONE_NUMBER_1 = "+5555123456789";
    private static final String MOCK_SEC_PHONE_NUMBER_1 = "+5555987654321";
    private static final String MOCK_CPF_CODE_1 = "07068093868";
    private static final String MOCK_NATIONALID_CODE_1 = "1234567";
    private static final LocalDate MOCK_BIRTHDAY_1 = LocalDate.of(1980, 2, 2);
    private static final LocalDateTime MOCK_CLIENT_SINCE_1 = LocalDateTime.now();
    private static final long MOCK_ID_1 = 1L;
    @Autowired
    private ClientService clientService;

    @MockBean
    private ClientRepository clientRepository;

    private Client mockClient1;
    private ClientForm mockClientForm1;

    @BeforeEach
    void setUp() {
        setUpMockClient();
        setUpMockClientForm();
    }

    private void setUpMockClientForm() {
        mockClientForm1 = new ClientForm();
        mockClientForm1.setEmail(MOCK_EMAIL_1);
        mockClientForm1.setFullName(MOCK_FULL_NAME_1);
        mockClientForm1.setAddressStreet(MOCK_ADDRESS_STREET_1);
        mockClientForm1.setAddressStreetNumber(MOCK_STREET_NUMBER_1);
        mockClientForm1.setAddressNeighborhood(MOCK_NEIGHBORHOOD_1);
        mockClientForm1.setAddressZipcode(MOCK_ZIPCODE_1);
        mockClientForm1.setAddressCity(MOCK_CITY_1);
        mockClientForm1.setAddressState(MOCK_STATE_1);
        mockClientForm1.setAddressCountry(MOCK_COUNTRY_1);
        mockClientForm1.setMainPhoneNumber(MOCK_MAIN_PHONE_NUMBER_1);
        mockClientForm1.setSecondaryPhoneNumber(MOCK_SEC_PHONE_NUMBER_1);
        mockClientForm1.setCpfCode(MOCK_CPF_CODE_1);
        mockClientForm1.setNationalIdCode(MOCK_NATIONALID_CODE_1);
        mockClientForm1.setBirthday(MOCK_BIRTHDAY_1);
    }

    private void setUpMockClient() {
        mockClient1 = new Client();
        mockClient1.setId(MOCK_ID_1);
        mockClient1.setEmail(MOCK_EMAIL_1);
        mockClient1.setFullName(MOCK_FULL_NAME_1);

        Address address = new Address();
        address.setStreet(MOCK_ADDRESS_STREET_1);
        address.setStreetNumber(MOCK_STREET_NUMBER_1);
        address.setNeighborhood(MOCK_NEIGHBORHOOD_1);
        address.setZipcode(MOCK_ZIPCODE_1);
        address.setCity(MOCK_CITY_1);
        address.setState(MOCK_STATE_1);
        address.setCountry(MOCK_COUNTRY_1);
        mockClient1.setAddress(address);

        mockClient1.setMainPhoneNumber(MOCK_MAIN_PHONE_NUMBER_1);
        mockClient1.setSecondaryPhoneNumber(MOCK_SEC_PHONE_NUMBER_1);
        mockClient1.setCpfCode(MOCK_CPF_CODE_1);
        mockClient1.setNationalIdCode(MOCK_NATIONALID_CODE_1);
        mockClient1.setBirthday(MOCK_BIRTHDAY_1);
        mockClient1.setClientSince(MOCK_CLIENT_SINCE_1);
    }

    @Test
    void testSaveSucessful() {
        // Setup our mock repository
        when(clientRepository.save(any(Client.class))).thenReturn(mockClient1);

        // Execute the service call
        Client returnedClient = clientService.save(mockClientForm1);

        // Assert the response
        assertNotNull(returnedClient);
        assertEquals(Client.class, returnedClient.getClass());
        assertNotNull(returnedClient.getId());
        assertEquals(MOCK_EMAIL_1, returnedClient.getEmail());
        assertEquals(MOCK_FULL_NAME_1, returnedClient.getFullName());
        assertNotNull(returnedClient.getAddress());
        assertEquals(MOCK_ADDRESS_STREET_1, returnedClient.getAddress().getStreet());
        assertEquals(MOCK_STREET_NUMBER_1, returnedClient.getAddress().getStreetNumber());
        assertEquals(MOCK_NEIGHBORHOOD_1, returnedClient.getAddress().getNeighborhood());
        assertEquals(MOCK_ZIPCODE_1, returnedClient.getAddress().getZipcode());
        assertEquals(MOCK_CITY_1, returnedClient.getAddress().getCity());
        assertEquals(MOCK_STATE_1, returnedClient.getAddress().getState());
        assertEquals(MOCK_COUNTRY_1, returnedClient.getAddress().getCountry());
        assertEquals(MOCK_MAIN_PHONE_NUMBER_1, returnedClient.getMainPhoneNumber());
        assertEquals(MOCK_SEC_PHONE_NUMBER_1, returnedClient.getSecondaryPhoneNumber());
        assertEquals(MOCK_CPF_CODE_1, returnedClient.getCpfCode());
        assertEquals(MOCK_NATIONALID_CODE_1, returnedClient.getNationalIdCode());
        assertEquals(MOCK_BIRTHDAY_1, returnedClient.getBirthday());
        assertTrue(
                returnedClient.getClientSince().until(MOCK_CLIENT_SINCE_1, ChronoUnit.MINUTES) < 2,
                "Signup datetime should be at most 2 minutes before now");
        assertNotNull(returnedClient.getPets());
        assertTrue(returnedClient.getPets().isEmpty());
    }

    @Test
    void testFindById(){

    }
}
