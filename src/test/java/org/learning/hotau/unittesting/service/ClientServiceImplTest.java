package org.learning.hotau.unittesting.service;

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
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

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
    private static final Long MOCK_ID_1 = 1L;
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
    void shouldSaveSucessfully() {
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
    void searchByIdShouldReturnCorrectClient_WhenItExists(){
        when(clientRepository.findById(eq(MOCK_ID_1)))
                .thenReturn(Optional.of(mockClient1));

        Client returnedClient = clientService.findById(MOCK_ID_1);

        assertEquals(mockClient1, returnedClient);
    }

    @Test
    void searchByIdShouldThrowException_WhenClientDoesNotExist() {
        long invalidId = -1;
        when(clientRepository.findById(invalidId))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> clientService.findById(invalidId));
    }

    @Test
    void getAllShouldReturnEmptyList_WhenThereIsNoClients() {
        when(clientRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<Client> returnedClients = clientService.findAll();

        assertNotNull(returnedClients);
        assertTrue(returnedClients.isEmpty());
    }

    @Test
    void getAllShouldReturnListWithElement_WhenThereIsClient() {
        List<Client> mockClientList = Collections.singletonList(mockClient1);

        when(clientRepository.findAll())
                .thenReturn(mockClientList);

        List<Client> returnedClients = clientService.findAll();

        assertEquals(mockClientList, returnedClients);
    }

    @Test
    void updateShouldBeSuccessful_WhenClientExists() {
        // Set up
        when(clientRepository.findById(eq(MOCK_ID_1)))
                .thenReturn(Optional.ofNullable(mockClient1));

        String updatedFullName = "João da Silva";

        Client updatedClient = mockClient1.toBuilder().build(); //Shallow copy
        updatedClient.setFullName(updatedFullName);

        ClientForm updatedClientForm = mockClientForm1.toBuilder().build(); //Shallow copy
        updatedClientForm.setFullName(updatedFullName);

        when(clientRepository.save(any(Client.class)))
                .thenReturn(mockClient1, updatedClient);

        // Call
        clientService.save(mockClientForm1);
        Client returnedClient = clientService.update(MOCK_ID_1, updatedClientForm);

        // Assert
        assertEquals(updatedClient, returnedClient);
    }

    @Test
    void updateShouldThrowException_WhenClientDoesNotExist() {
        // Set up
        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        Long nonExistingId = -1L;

        assertThrows(NoSuchElementException.class, () -> clientService.update(nonExistingId, mockClientForm1));
    }

    @Test
    void deleteShouldBeSuccessful_WhenClientExists() {
        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        when(clientRepository.existsById(anyLong()))
                .thenReturn(true);

        assertDoesNotThrow(() -> clientService.deleteById(MOCK_ID_1));
        assertThrows(NoSuchElementException.class, () -> clientService.findById(MOCK_ID_1));
    }

    @Test
    void deleteShouldThrowException_WhenClientDoesNotExist() {
        when(clientRepository.existsById(anyLong()))
                .thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> clientService.deleteById(MOCK_ID_1));
    }

    @Test
    void createShouldBeSuccessful_WhenCpfCodeIsUnique() {
        String newCpfCode = "14348725055"; // Random generated

        Client mockClient2 = mockClient1.toBuilder().build();
        mockClient2.setCpfCode(newCpfCode);

        ClientForm mockClientForm2 = mockClientForm1.toBuilder().build();
        mockClientForm2.setCpfCode(newCpfCode);

        when(clientRepository.save(any(Client.class)))
                .thenReturn(mockClient1)
                .thenReturn(mockClient2);

        assertDoesNotThrow(() -> clientService.save(mockClientForm1));
        assertDoesNotThrow(() -> clientService.save(mockClientForm2));
    }

    @Test
    void createShouldThrowException_WhenCpfCodeIsNotUnique() {
        when(clientRepository.save(any(Client.class)))
                .thenReturn(mockClient1)
                .thenThrow(DuplicateKeyException.class);

        assertDoesNotThrow(() -> clientService.save(mockClientForm1));
        assertThrows(DuplicateKeyException.class, () -> clientService.save(mockClientForm1));
    }

    @Test
    void updateShouldThrowException_WhenCpfCodeIsUpdatedToExistingValue() {
        when(clientRepository.findById(MOCK_ID_1))
                .thenReturn(Optional.ofNullable(mockClient1));

        when(clientRepository.save(any(Client.class)))
                .thenThrow(DuplicateKeyException.class);

        assertThrows(DuplicateKeyException.class, () -> clientService.update(MOCK_ID_1, mockClientForm1));
    }

    @Test
    void filterByCpfCodeShouldReturnOneClient_WhenItIsRegistered() {
        when(clientRepository.findAllByCpfCode(anyString()))
                .thenReturn(Collections.singletonList(mockClient1));

        List<Client> returnedClients = clientService.filterByCpfCode(MOCK_CPF_CODE_1);
        assertFalse(returnedClients.isEmpty());
        assertEquals(mockClient1, returnedClients.get(0));
    }

    @Test
    void filterByCpfCodeShouldEmptyArray_WhenItIsNotRegistered() {
        when(clientRepository.findAllByCpfCode(anyString()))
                .thenReturn(new ArrayList<>());

        assertTrue(clientService.filterByCpfCode(MOCK_CPF_CODE_1).isEmpty());
    }
}
