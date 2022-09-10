package org.learning.hotau.unittesting.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.learning.hotau.dto.form.ProductForm;
import org.learning.hotau.model.Product;
import org.learning.hotau.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    private static final long MOCK_SERVICE_ID = 1L;
    private static final String MOCK_SERVICE_NAME = "Dogwalk";
    private static final String MOCK_SERVICE_DESCRIPTION = "We walk with your dogs in the sunset!";
    private static final BigDecimal MOCK_SERVICE_PRICE = BigDecimal.valueOf(40.0);

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper mapper;

    @MockBean
    ProductService productService;

    private Product mockProduct;

    private ProductForm mockProductForm;

    private static final String BASE_URL = "/products";

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
    void shouldSaveSuccessfully() throws Exception {
        when(productService.create(any(ProductForm.class)))
                .thenReturn(mockProduct);

        mockMvc.perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockProductForm)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(MOCK_SERVICE_ID))
                .andExpect(jsonPath("$.name").value(MOCK_SERVICE_NAME))
                .andExpect(jsonPath("$.description").value(MOCK_SERVICE_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(MOCK_SERVICE_PRICE));
    }

    @Test
    void findAllShouldReturnProducts_WhenTheyExist() throws Exception {
        when(productService.findAll())
                .thenReturn(Collections.singletonList(mockProduct));

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$[0].id").value(MOCK_SERVICE_ID));
    }

    @Test
    void findAllShouldReturnEmptyList_WhenTheyDontExist() throws Exception {
        when(productService.findAll())
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    void findByIdShouldReturnProduct_WhenItExists() throws Exception {
        when(productService.findById(anyLong()))
                .thenReturn(mockProduct);

        mockMvc.perform(get(BASE_URL + "/" + MOCK_SERVICE_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isNotEmpty())
                .andExpect(jsonPath("$.id").value(MOCK_SERVICE_ID))
                .andExpect(jsonPath("$.name").value(MOCK_SERVICE_NAME))
                .andExpect(jsonPath("$.description").value(MOCK_SERVICE_DESCRIPTION))
                .andExpect(jsonPath("$.price").value(MOCK_SERVICE_PRICE));
    }

    @Test
    void findByIdShouldReturnError_WhenItDoesNotExist() throws Exception {
        when(productService.findById(anyLong()))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(get(BASE_URL + "/" + MOCK_SERVICE_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void updateShouldBeSuccessful_WhenProductExists() throws Exception {
        when(productService.update(anyLong(), any(ProductForm.class)))
                .thenReturn(mockProduct);

        mockMvc.perform(put(BASE_URL + "/" + MOCK_SERVICE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockProductForm)))
                .andExpect(status().isOk());
    }

    @Test
    void updateShouldReturnError_WhenProductDoesNotExist() throws Exception {
        when(productService.update(anyLong(), any(ProductForm.class)))
                .thenThrow(NoSuchElementException.class);

        mockMvc.perform(put(BASE_URL + "/" + MOCK_SERVICE_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockProductForm)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteShouldBeSuccessful_WhenProductExists() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + MOCK_SERVICE_ID))
                .andExpect(status().isOk());
    }

    @Test
    void deleteShouldReturnError_WhenProductDoesNotExist() throws Exception {
        doThrow(NoSuchElementException.class)
                .when(productService).deleteById(anyLong());

        mockMvc.perform(delete(BASE_URL + "/" + MOCK_SERVICE_ID))
                .andExpect(status().isNotFound());
    }
}
