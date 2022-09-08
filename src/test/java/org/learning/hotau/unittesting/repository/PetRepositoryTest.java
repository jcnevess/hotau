package org.learning.hotau.unittesting.repository;

import org.junit.jupiter.api.Test;
import org.learning.hotau.model.Pet;
import org.learning.hotau.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class PetRepositoryTest {

    @Autowired
    PetRepository petRepository;

    private final long validID = 1L;
    private final long invalidID = -1L;

    @Test
    void testFindAllByOwner() {
        List<Pet> petsFoundForValidOwner = petRepository.findAllByOwnerId(validID);

        assertNotNull(petsFoundForValidOwner);
        assertEquals(1, petsFoundForValidOwner.size());
        assertEquals(1L, petsFoundForValidOwner.get(0).getId());
        assertEquals("Docinho", petsFoundForValidOwner.get(0).getName());

        List<Pet> petsFoundForInvalidOwner = petRepository.findAllByOwnerId(invalidID);

        assertNotNull(petsFoundForInvalidOwner);
        assertTrue(petsFoundForInvalidOwner.isEmpty());

    }
}
