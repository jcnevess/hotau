package org.learning.hotau.unittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.ClientForm;
import org.learning.hotau.model.Address;
import org.learning.hotau.model.Client;
import org.learning.hotau.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {

    private static final String REQUEST_ROOT_URL = "/clients";
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

    private Client mockClient1;
    private ClientForm mockClientForm1;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ClientServiceImpl clientService;

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
    void shouldCreateValidClient() throws Exception {
        //Setup mock
        when(clientService.save(any(ClientForm.class)))
                .thenReturn(mockClient1);

        // Call
        mockMvc.perform(post(REQUEST_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                //Check
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", REQUEST_ROOT_URL + "/" + MOCK_ID_1))
                .andExpect(jsonPath("$.id").value(MOCK_ID_1));
    }

    @Test
    void searchByIdShouldReturnCorrectClient_WhenItExists() throws Exception{
        when(clientService.findById(eq(MOCK_ID_1)))
                .thenReturn(mockClient1);

        mockMvc.perform(get(REQUEST_ROOT_URL+ "/" + MOCK_ID_1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(MOCK_ID_1))
                .andExpect(jsonPath("$.email").value(MOCK_EMAIL_1))
                .andExpect(jsonPath("$.fullName").value(MOCK_FULL_NAME_1))
                .andExpect(jsonPath("$.address.street").value(MOCK_ADDRESS_STREET_1))
                .andExpect(jsonPath("$.address.streetNumber").value(MOCK_STREET_NUMBER_1))
                .andExpect(jsonPath("$.address.neighborhood").value(MOCK_NEIGHBORHOOD_1))
                .andExpect(jsonPath("$.address.zipcode").value(MOCK_ZIPCODE_1))
                .andExpect(jsonPath("$.address.city").value(MOCK_CITY_1))
                .andExpect(jsonPath("$.address.state").value(MOCK_STATE_1))
                .andExpect(jsonPath("$.address.country").value(MOCK_COUNTRY_1))
                .andExpect(jsonPath("$.mainPhoneNumber").value(MOCK_MAIN_PHONE_NUMBER_1))
                .andExpect(jsonPath("$.secondaryPhoneNumber").value(MOCK_SEC_PHONE_NUMBER_1))
                .andExpect(jsonPath("$.cpfCode").value(MOCK_CPF_CODE_1))
                .andExpect(jsonPath("$.nationalIdCode").value(MOCK_NATIONALID_CODE_1))
                .andExpect(jsonPath("$.birthday").value(MOCK_BIRTHDAY_1.toString()));
    }

    @Test
    void searchByIdShouldReturnNotFound_WhenClientDoesNotExist() throws Exception {
        long invalidId = -1;

        when(clientService.findById(eq(invalidId)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(REQUEST_ROOT_URL + "/" + invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllShouldReturnEmptyList_WhenThereIsNoClients() throws Exception {
        when(clientService.findAll())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(REQUEST_ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void getAllShouldReturnListWithElement_WhenThereIsClient() throws Exception {
        List<Client> mockClientList = Collections.singletonList(mockClient1);

        when(clientService.findAll())
                .thenReturn(mockClientList);

        mockMvc.perform(get(REQUEST_ROOT_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(MOCK_ID_1));
    }

    @Test
    void updateShouldBeSuccessful_WhenClientExists() throws Exception {
        when(clientService.update(anyLong(), any(ClientForm.class)))
                .thenReturn(mockClient1);

        mockMvc.perform(put(REQUEST_ROOT_URL + "/" + MOCK_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                .andExpect(status().isOk());
    }

    @Test
    void updateShouldReturnNotFound_WhenClientDoesNotExist() throws Exception {
        when(clientService.update(anyLong(), any(ClientForm.class)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(REQUEST_ROOT_URL + "/" + MOCK_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldBeSuccessful_WhenClientExists() throws Exception {
        mockMvc.perform(delete(REQUEST_ROOT_URL + "/" + MOCK_ID_1))
                .andExpect(status().isOk());
    }

    @Test
    void deleteShouldReturnNotFound_WhenClientDoesNotExist() throws Exception {
        doThrow(NoSuchElementException.class)
                .when(clientService).deleteById(anyLong());

        long invalidId = -1L;
        mockMvc.perform(delete(REQUEST_ROOT_URL + "/" + invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createShouldBeSuccessful_WhenCpfCodeIsUnique() throws Exception {
        String newCpfCode = "14348725055"; // Random generated

        Client mockClient2 = mockClient1.toBuilder().build();
        mockClient2.setCpfCode(newCpfCode);

        ClientForm mockClientForm2 = mockClientForm1.toBuilder().build();
        mockClientForm2.setCpfCode(newCpfCode);

        when(clientService.save(any(ClientForm.class)))
                .thenReturn(mockClient1)
                .thenReturn(mockClient2);

        mockMvc.perform(post(REQUEST_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpfCode").value(MOCK_CPF_CODE_1));

        mockMvc.perform(post(REQUEST_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm2)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpfCode").value(newCpfCode));
    }

    @Test
    void createShouldReturnError_WhenCpfCodeIsNotUnique() throws Exception {
        when(clientService.save(any(ClientForm.class)))
                .thenReturn(mockClient1)
                .thenThrow(DuplicateKeyException.class);

        mockMvc.perform(post(REQUEST_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cpfCode").value(MOCK_CPF_CODE_1));

        mockMvc.perform(post(REQUEST_ROOT_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void updateShouldReturnError_WhenCpfCodeIsUpdatedToExistingValue() throws Exception {
        when(clientService.update(anyLong(), any(ClientForm.class)))
                .thenThrow(DuplicateKeyException.class);

        mockMvc.perform(put(REQUEST_ROOT_URL + "/" + MOCK_ID_1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists())
                .andExpect(jsonPath("$.message").isNotEmpty());
    }

    @Test
    void filterByCpfCodeShouldReturnOneClient_WhenItsRegistered() throws Exception {
        when(clientService.filterByCpfCode(anyString()))
                .thenReturn(Collections.singletonList(mockClient1));

        mockMvc.perform(get(REQUEST_ROOT_URL)
                        .param("cpfCode", MOCK_CPF_CODE_1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[0].id").value(MOCK_ID_1))
                .andExpect(jsonPath("$[1]").doesNotExist());
    }

    @Test
    void filterByCpfCodeShouldReturnEmptyArray_WhenItsNotRegistered() throws Exception {
        when(clientService.filterByCpfCode(anyString()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(REQUEST_ROOT_URL)
                        .param("cpfCode", MOCK_CPF_CODE_1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }


}
