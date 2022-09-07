package org.learning.hotau.controller;

import org.learning.hotau.dto.form.PetForm;
import org.learning.hotau.model.Pet;
import org.learning.hotau.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final static String BASE_URL = "/pets";

    @Autowired
    PetService petService;

    @PostMapping
    public ResponseEntity<Pet> create(@Valid @RequestBody PetForm form){
        Pet newPet = petService.save(form);

        return ResponseEntity.created(URI.create(BASE_URL + "/" + newPet.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newPet);
    }

    @GetMapping
    public ResponseEntity<Iterable<Pet>> findAll() {
        return ResponseEntity.ok(petService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pet> findById(@PathVariable Long id) {
        return ResponseEntity.ok(petService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Pet> update(@PathVariable Long id, @Valid @RequestBody PetForm form) {
        petService.update(id, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Pet> delete(@PathVariable Long id){
        petService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
