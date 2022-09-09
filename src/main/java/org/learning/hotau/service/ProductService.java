package org.learning.hotau.service;

import org.learning.hotau.dto.form.ProductForm;
import org.learning.hotau.model.Product;

import java.util.List;

public interface ProductService {

    Product create(ProductForm form);

    void deleteById(Long id);

    List<Product> findAll();

    Product findById(Long id);

    Product update(Long id, ProductForm form);
}
