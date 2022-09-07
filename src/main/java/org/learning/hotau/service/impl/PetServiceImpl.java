package org.learning.hotau.service.impl;

import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.model.Pet;
import org.learning.hotau.repository.PetRepository;
import org.learning.hotau.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PetServiceImpl implements PetService {

    @Autowired
    PetRepository petRepository;

    @Override
    public Pet save(PetForm form) {
        return petRepository.save(
            Pet.builder()
                    .name(form.getName())
                    .age(form.getAge())
                    .birthday(form.getBirthday())
                    .isNeutered(form.isNeutered())
                    .breed(form.getBreed())
                    .sex(form.getSex())
                    .build()
        );
    }

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @Override
    public Pet update(Long id, PetForm form) {
        return petRepository.findById(id)
                .map(pet -> {
                    pet.setName(form.getName());
                    pet.setAge(form.getAge());
                    pet.setBirthday(form.getBirthday());
                    pet.setNeutered(form.isNeutered());
                    pet.setBreed(form.getBreed());
                    pet.setSex(form.getSex());

                    return petRepository.save(pet);
                })
                .orElseThrow();
    }

    @Override
    public void deleteById(Long id) {
        petRepository.findById(id).ifPresentOrElse(
                pet -> petRepository.deleteById(id),
                () -> { throw new NoSuchElementException(); }
        );
    }
}
