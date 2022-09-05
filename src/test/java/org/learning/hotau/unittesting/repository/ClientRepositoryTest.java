package org.learning.hotau.unittesting.repository;

import org.junit.jupiter.api.Test;
import org.learning.hotau.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void testFilterByCpfCode() {
        assertFalse(clientRepository.findAllByCpfCode("07068093868").isEmpty());
        assertEquals("07068093868", clientRepository.findAllByCpfCode("07068093868").get(0).getCpfCode());
        assertTrue(clientRepository.findAllByCpfCode("98765432100").isEmpty());
    }
}
