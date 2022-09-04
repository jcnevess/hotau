package org.learning.hotau.controller;

import org.learning.hotau.dto.form.ClientForm;
import org.learning.hotau.model.Client;
import org.learning.hotau.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientServiceImpl clientService;

    @PostMapping
    public ResponseEntity<Client> create(@Valid @RequestBody ClientForm form) {
        Client newClient = clientService.save(form);

        return ResponseEntity.created(URI.create("/clients/" + newClient.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newClient);
    }

    @GetMapping
    public ResponseEntity<Iterable<Client>> getAll() {
        return ResponseEntity.ok(clientService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> get(@PathVariable Long id) {
        return ResponseEntity.ok(clientService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client> update(@PathVariable Long id, @Valid @RequestBody ClientForm form) {
        clientService.update(id, form);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Client> delete(@PathVariable Long id) {
        clientService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
