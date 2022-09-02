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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientControllerTest {
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
    void testCreateValidClient() throws Exception {
        //Setup mock
        when(clientService.save(any(ClientForm.class))).thenReturn(mockClient1);

        // Call
        mockMvc.perform(post("/client")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockClientForm1)))
                //Check
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "/client/1"))
                .andExpect(jsonPath("$.id").value(MOCK_ID_1));
    }
}
