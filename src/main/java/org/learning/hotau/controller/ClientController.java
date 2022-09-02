package org.learning.hotau.controller;

import org.learning.hotau.dto.form.ClientForm;
import org.learning.hotau.model.Client;
import org.learning.hotau.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody ClientForm form) {
        return new ResponseEntity<>(clientService.save(form), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Iterable<Client>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }
}
