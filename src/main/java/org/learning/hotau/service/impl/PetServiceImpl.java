package org.learning.hotau.service.impl;

import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.exception.InvalidReferenceException;
import org.learning.hotau.model.Pet;
import org.learning.hotau.repository.ClientRepository;
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

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Pet save(PetForm form) {
        return clientRepository.findById(form.getOwnerId()).map(
            client -> petRepository.save(
                    Pet.builder()
                            .name(form.getName())
                            .age(form.getAge())
                            .birthday(form.getBirthday())
                            .isNeutered(form.isNeutered())
                            .breed(form.getBreed())
                            .sex(form.getSex())
                            .owner(client)
                            .build())
        ).orElseThrow(() -> new InvalidReferenceException("Client not found"));
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
        return clientRepository.findById(form.getOwnerId())
            .map(client -> petRepository.findById(id)
                .map(pet -> {
                    Pet updatedPet = Pet.builder()
                            .name(form.getName())
                            .age(form.getAge())
                            .birthday(form.getBirthday())
                            .isNeutered(form.isNeutered())
                            .breed(form.getBreed())
                            .sex(form.getSex())
                            .owner(client)
                            .build();
                    return petRepository.save(updatedPet);
                })
                .orElseThrow())
            .orElseThrow(() -> { throw new InvalidReferenceException("Client not found"); });
    }

    @Override
    public void deleteById(Long id) {
        petRepository.findById(id).ifPresentOrElse(
                pet -> petRepository.deleteById(id),
                () -> { throw new NoSuchElementException(); }
        );
    }

    @Override
    public List<Pet> filterByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }
}
