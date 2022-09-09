package org.learning.hotau.unittesting.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.ProductForm;
import org.learning.hotau.model.Product;
import org.learning.hotau.repository.ProductRepository;
import org.learning.hotau.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ProductServiceImplTest {

    private static final long MOCK_SERVICE_ID = 1L;
    private static final String MOCK_SERVICE_NAME = "Dogwalk";
    private static final String MOCK_SERVICE_DESCRIPTION = "We walk with your dogs in the sunset!";
    private static final BigDecimal MOCK_SERVICE_PRICE = BigDecimal.valueOf(40.0);

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository productRepository;

    private Product mockProduct;

    private ProductForm mockProductForm;

    @BeforeEach
    void setUp() {
        mockProduct = Product.builder()
                .id(MOCK_SERVICE_ID)
                .name(MOCK_SERVICE_NAME)
                .description(MOCK_SERVICE_DESCRIPTION)
                .price(MOCK_SERVICE_PRICE)
                .build();

        mockProductForm = ProductForm.builder()
                .name(MOCK_SERVICE_NAME)
                .description(MOCK_SERVICE_DESCRIPTION)
                .price(MOCK_SERVICE_PRICE)
                .build();
    }

    @Test
    void createServiceShouldBeSuccessful() {
        when(productRepository.save(any(Product.class)))
                .thenReturn(mockProduct);

        Product savedProduct = productService.create(mockProductForm);

        assertEquals(mockProduct, savedProduct);
    }

    @Test
    void deleteServiceShouldBeSuccessful_WhenItExists() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockProduct));

        assertDoesNotThrow(() -> productService.deleteById(MOCK_SERVICE_ID));
    }

    @Test
    void deleteServiceShouldThrowException_WhenItDoesNotExists() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.deleteById(MOCK_SERVICE_ID));
    }

    @Test
    void findAllShouldReturnNonEmptyList() {
        when(productRepository.findAll())
                .thenReturn(Collections.singletonList(mockProduct));

        List<Product> products = productService.findAll();

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals(MOCK_SERVICE_ID, products.get(0).getId());
    }

    @Test
    void findByIdShouldReturnProduct_WhenItExists() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockProduct));

        Product foundProduct = productService.findById(MOCK_SERVICE_ID);

        assertNotNull(foundProduct);
        assertEquals(mockProduct, foundProduct);
    }

    @Test
    void findByIdShouldThrowException_WhenItDoesNotExist() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.findById(MOCK_SERVICE_ID));
    }

    @Test
    void updateShouldBeSuccessful_WhenProductExists() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.ofNullable(mockProduct));

        when(productRepository.save(any(Product.class)))
                .thenReturn(mockProduct);

        Product updatedProduct = productService.update(MOCK_SERVICE_ID, mockProductForm);

        assertNotNull(updatedProduct);
        assertEquals(MOCK_SERVICE_ID, updatedProduct.getId());
    }

    @Test
    void updateShouldThrowException_WhenProductDoesNotExist() {
        when(productRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.update(MOCK_SERVICE_ID, mockProductForm));
    }
}


