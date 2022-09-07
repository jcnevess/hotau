package org.learning.hotau.service;

import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.model.Pet;

import java.util.List;

public interface PetService {

    Pet save(PetForm form);

    Pet findById(Long id);

    List<Pet> findAll();

    Pet update(Long id, PetForm form);

    void deleteById(Long id);

}
