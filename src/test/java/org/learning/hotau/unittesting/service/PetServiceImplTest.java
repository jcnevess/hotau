package org.learning.hotau.unittesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.exception.InvalidReferenceException;
import org.learning.hotau.model.Address;
import org.learning.hotau.model.Client;
import org.learning.hotau.model.Pet;
import org.learning.hotau.repository.ClientRepository;
import org.learning.hotau.repository.PetRepository;
import org.learning.hotau.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private static final long PET_OWNER_ID_MOCK = 1L;

    @MockBean
    private PetRepository petRepository;

    @MockBean
    private ClientRepository clientRepository;

    @Autowired
    private PetService petService;

    private Pet mockPet;
    private PetForm mockPetForm;
    private Client mockClient;

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
                .owner(mockClient)
                .build();

        mockPetForm = PetForm.builder()
                .name(PET_NAME_MOCK)
                .age(PET_AGE_MOCK)
                .birthday(PET_BIRTHDAY_MOCK)
                .isNeutered(PET_NEUTERED_MOCK)
                .breed(PET_BREED_MOCK)
                .sex(PET_SEX_MOCK)
                .ownerId(PET_OWNER_ID_MOCK)
                .build();

        mockClient = Client.builder()
                .email("jose@mail.com")
                .fullName("José")
                .address(Address.builder()
                                .street("Rua Projetada")
                                .streetNumber("S/N")
                                .neighborhood("New City")
                                .zipcode("55555-55")
                                .city("Townpolis")
                                .state("New River")
                                .country("Ocean")
                                .build())
                .mainPhoneNumber("+5555999888777")
                .secondaryPhoneNumber("+5555666555444")
                .cpfCode("987654321-00")
                .nationalIdCode("1234678")
                .birthday(LocalDate.now())
                .clientSince(LocalDateTime.now())
                .build();
    }

    @Test
    void saveShouldBeSuccessful() {
        when(petRepository.save(any(Pet.class)))
                .thenReturn(mockPet);

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockClient));

        Pet savedPet = petService.save(mockPetForm);

        assertEquals(mockPet, savedPet);
    }

    @Test
    void saveShouldFail_WhenOwnerIdIsInvalid() {
        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(InvalidReferenceException.class, () -> petService.save(mockPetForm));
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

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockClient));

        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockPet));

        when(petRepository.save(any(Pet.class)))
                .thenReturn(updatedMockPet);

        Pet updatedPet = petService.update(PET_ID_MOCK, updatedMockPetForm);

        assertNotNull(updatedPet);
        assertEquals(updatedMockPet, updatedPet);
    }

    @Test
    void updateShouldThrowException_WhenPetDoesNotExist() {
        String updatedBreed = "Pinscher";

        PetForm updatedMockPetForm = mockPetForm.toBuilder().build(); //Shallow copy
        updatedMockPetForm.setBreed(updatedBreed);

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockClient));

        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> petService.update(PET_ID_MOCK, updatedMockPetForm));
    }

    @Test
    void updateShouldThrowException_WhenOwnerIsNotFound() {
        String updatedBreed = "Pinscher";

        PetForm updatedMockPetForm = mockPetForm.toBuilder().build(); //Shallow copy
        updatedMockPetForm.setBreed(updatedBreed);

        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(InvalidReferenceException.class, () -> petService.update(PET_ID_MOCK, updatedMockPetForm));
    }

    @Test
    void deleteShouldBeSuccessful_WhenPetExists() {
        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockPet));

        assertDoesNotThrow(() -> petService.deleteById(PET_ID_MOCK));
    }

    @Test
    void deleteShouldThrowException_WhenPetDoesNotExist() {
        when(petRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> petService.deleteById(PET_ID_MOCK));
    }

    @Test
    void filterByOwnerShouldReturnPets_WhenOwnerExists() {
        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockClient));

        when(petRepository.findAllByOwnerId(anyLong()))
                .thenReturn(Collections.singletonList(mockPet));

        List<Pet> foundPets = petService.filterByOwnerId(PET_OWNER_ID_MOCK);

        assertNotNull(foundPets);
        assertFalse(foundPets.isEmpty());
        assertEquals(1, foundPets.size());
        assertEquals(PET_ID_MOCK, foundPets.get(0).getId());
    }

    @Test
    void filterByOwnerShouldReturnEmptyList_WhenOwnerDoesNotExist() {
        when(clientRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        List<Pet> foundPets = petService.filterByOwnerId(PET_OWNER_ID_MOCK);

        assertNotNull(foundPets);
        assertTrue(foundPets.isEmpty());
    }
}
