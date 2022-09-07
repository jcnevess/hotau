package org.learning.hotau.unittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.model.Pet;
import org.learning.hotau.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class PetControllerTest {
    private static final long PET_ID_MOCK = 1L;
    private static final String PET_NAME_MOCK = "Docinho";
    private static final LocalDate PET_BIRTHDAY_MOCK = LocalDate.of(2015, Month.FEBRUARY, 2);
    private static final boolean PET_NEUTERED_MOCK = true;
    private static final String PET_BREED_MOCK = "Pit Bull";
    private static final String PET_SEX_MOCK = "Male";
    private static final int PET_AGE_MOCK = 7;
    private static final String BASE_URL = "/pets";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private PetService petService;

    private Pet mockPet;
    private PetForm mockPetForm;

    @BeforeEach
    void setUp() {
        mockPet = Pet.builder()
                .id(PET_ID_MOCK)
                .name(PET_NAME_MOCK)
                .age(PET_AGE_MOCK)
                .birthday(PET_BIRTHDAY_MOCK)
                .isNeutered(PET_NEUTERED_MOCK)
                .breed(PET_BREED_MOCK)
                .sex(PET_SEX_MOCK)
                .build();

        mockPetForm = PetForm.builder()
                .name(PET_NAME_MOCK)
                .age(PET_AGE_MOCK)
                .birthday(PET_BIRTHDAY_MOCK)
                .isNeutered(PET_NEUTERED_MOCK)
                .breed(PET_BREED_MOCK)
                .sex(PET_SEX_MOCK)
                .build();
    }

    @Test
    void createShouldBeSuccessful() throws Exception {
        when(petService.save(any(PetForm.class)))
                .thenReturn(mockPet);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockPetForm)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", BASE_URL + "/" + PET_ID_MOCK))
                .andExpect(jsonPath("$.id").value(PET_ID_MOCK));
    }

    @Test
    void searchAllShouldReturnPets_WhenTheyAreSaved() throws Exception {
        when(petService.findAll())
                .thenReturn(Collections.singletonList(mockPet));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0]").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(PET_ID_MOCK));
    }

    @Test
    void searchAllShouldReturnEmptyList_WhenTheyAreNotPets() throws Exception {
        when(petService.findAll())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void searchByIdShouldReturnPet_WhenItExists() throws Exception {
        when(petService.findById(eq(PET_ID_MOCK)))
                .thenReturn(mockPet);

        mockMvc.perform(get(URI.create(BASE_URL + "/" + PET_ID_MOCK)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.id").value(PET_ID_MOCK))
                .andExpect(jsonPath("$.name").value(PET_NAME_MOCK))
                .andExpect(jsonPath("$.age").value(PET_AGE_MOCK))
                .andExpect(jsonPath("$.birthday").value(PET_BIRTHDAY_MOCK.toString()))
                .andExpect(jsonPath("$.neutered").value(PET_NEUTERED_MOCK))
                .andExpect(jsonPath("$.breed").value(PET_BREED_MOCK))
                .andExpect(jsonPath("$.sex").value(PET_SEX_MOCK))
                .andExpect(jsonPath("$.notes").isArray())
                .andExpect(jsonPath("$.notes").isEmpty());
    }

    @Test
    void searchByIdShouldReturnNotFound_WhenPetDoesNotExist() throws Exception {
        long nonExistingId = -1L;

        when(petService.findById(eq(nonExistingId)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(URI.create(BASE_URL + "/" + nonExistingId)))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateShouldBeSuccessful_WhenPetExists() throws Exception {
        String updatedBreed = "Pinscher";

        Pet updatedMockPet = mockPet.toBuilder().build(); //Shallow copy
        updatedMockPet.setBreed(updatedBreed);

        PetForm updatedMockPetForm = mockPetForm.toBuilder().build(); //Shallow copy
        updatedMockPetForm.setBreed(updatedBreed);

        when(petService.update(anyLong(), any(PetForm.class)))
                .thenReturn(updatedMockPet);

        mockMvc.perform(put(URI.create(BASE_URL + "/" + PET_ID_MOCK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedMockPetForm)))
                .andExpect(status().isOk());
    }

    @Test
    void updateShouldReturnNotFound_WhenPetDoesNotExist() throws Exception {
        String updatedBreed = "Pinscher";

        PetForm updatedMockPetForm = mockPetForm.toBuilder().build(); //Shallow copy
        updatedMockPetForm.setBreed(updatedBreed);

        when(petService.update(anyLong(), any(PetForm.class)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(URI.create(BASE_URL + "/" + PET_ID_MOCK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(updatedMockPetForm)))
                .andExpect(status().isNotFound());

    }

    @Test
    void deleteByIdShouldBeSuccessful_WhenPetExists() throws Exception {
        mockMvc.perform(delete(URI.create(BASE_URL + "/" + PET_ID_MOCK)))
                .andExpect(status().isOk());
    }

    @Test
    void deleteByIdShouldReturnNotFound_WhenPetDoesNotExist() throws Exception {
        doThrow(NoSuchElementException.class)
                .when(petService).deleteById(anyLong());

        long invalidId = -1L;
        mockMvc.perform(delete(URI.create(BASE_URL + "/" + invalidId)))
                .andExpect(status().isNotFound());
    }
}
