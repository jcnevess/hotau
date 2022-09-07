package org.learning.hotau.unittesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.model.Pet;
import org.learning.hotau.repository.PetRepository;
import org.learning.hotau.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PetServiceImplTest {

    private static final long PET_ID_MOCK = 1L;
    private static final String PET_NAME_MOCK = "Docinho";
    private static final LocalDate PET_BIRTHDAY_MOCK = LocalDate.of(2015, Month.FEBRUARY, 2);
    private static final boolean PET_NEUTERED_MOCK = true;
    private static final String PET_BREED_MOCK = "Pit Bull";
    private static final String PET_SEX_MOCK = "Male";
    private static final int PET_AGE_MOCK = 7;
    @MockBean
    private PetRepository petRepository;

    @Autowired
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
    void saveShouldBeSuccessful() {
        when(petRepository.save(any(Pet.class)))
                .thenReturn(mockPet);

        Pet savedPet = petService.save(mockPetForm);

        assertEquals(mockPet, savedPet);
    }

    @Test
    void searchAllShouldReturnPets_WhenTheyAreSaved() {
        when(petRepository.findAll())
                .thenReturn(Collections.singletonList(mockPet));

        List<Pet> pets = petService.findAll();

        assertFalse(pets.isEmpty());
        assertEquals(1, pets.size());
        assertEquals(mockPet, pets.get(0));
    }

    @Test
    void searchAllShouldReturnEmptyList_WhenThereAreNoPets() {
        when(petRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<Pet> foundPets = petService.findAll();

        assertNotNull(foundPets);
        assertTrue(foundPets.isEmpty());
    }

    @Test
    void searchByIdShouldBeSuccessful_WhenPetExists() {
        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockPet));

        Pet foundPet = petService.findById(PET_ID_MOCK);

        assertNotNull(foundPet);
        assertEquals(mockPet, foundPet);
    }

    @Test
    void searchByIdShouldThrowException_WhenPetDoesNotExist() {
        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> petService.findById(PET_ID_MOCK));
    }

    @Test
    void updateShouldBeSuccessful_WhenPetExists() {
        String updatedBreed = "Pinscher";

        Pet updatedMockPet = mockPet.toBuilder().build(); //Shallow copy
        updatedMockPet.setBreed(updatedBreed);

        PetForm updatedMockPetForm = mockPetForm.toBuilder().build(); //Shallow copy
        updatedMockPetForm.setBreed(updatedBreed);

        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockPet));

        when(petRepository.save(any(Pet.class)))
                .thenReturn(updatedMockPet);

        Pet updatedPet = petService.update(PET_ID_MOCK, updatedMockPetForm);

        assertNotNull(updatedPet);
        assertEquals(updatedMockPet, updatedPet);
    }

    @Test
    void updateShouldReturnError_WhenPetDoesNotExist() {
        String updatedBreed = "Pinscher";

        PetForm updatedMockPetForm = mockPetForm.toBuilder().build(); //Shallow copy
        updatedMockPetForm.setBreed(updatedBreed);

        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> petService.update(PET_ID_MOCK, updatedMockPetForm));
    }

    @Test
    void deleteShouldBeSuccessful_WhenPetExists() {
        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockPet));

        assertDoesNotThrow(() -> petService.deleteById(PET_ID_MOCK));
    }

    @Test
    void deleteShouldThrowException_WhenPetDoesNotExist() {
        when(petRepository.existsById(anyLong()))
                .thenReturn(false);

        assertThrows(NoSuchElementException.class, () -> petService.deleteById(PET_ID_MOCK));
    }
}
