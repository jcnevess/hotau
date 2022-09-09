package org.learning.hotau.service.impl;

import org.learning.hotau.dto.form.ProductForm;
import org.learning.hotau.model.Product;
import org.learning.hotau.repository.ProductRepository;
import org.learning.hotau.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product create(ProductForm form) {
        Product newProduct = Product.builder()
                .name(form.getName())
                .description(form.getDescription())
                .price(form.getPrice())
                .build();

        return productRepository.save(newProduct);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.findById(id).ifPresentOrElse(
                product -> productRepository.deleteById(id),
                () -> { throw new NoSuchElementException(); }
        );
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Product update(Long id, ProductForm form) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setName(form.getName());
                    product.setDescription(form.getDescription());
                    product.setPrice(form.getPrice());

                    return productRepository.save(product);
                })
                .orElseThrow();
    }
}
