package org.learning.hotau.controller;

import org.learning.hotau.dto.form.ProductForm;
import org.learning.hotau.model.Product;
import org.learning.hotau.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    private static final String BASE_URL = "/products";

    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductForm form) {
        Product newProduct = productService.create(form);

        return ResponseEntity.created(URI.create(BASE_URL))
                .contentType(MediaType.APPLICATION_JSON)
                .body(newProduct);
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody ProductForm form) {
        productService.update(id, form);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
