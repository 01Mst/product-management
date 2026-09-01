package com.thulasimani.product_management.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thulasimani.product_management.dto.response.ProductResponse;
import com.thulasimani.product_management.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    @Test
    void getProductById_shouldReturn200() throws Exception {

        ProductResponse response =
                new ProductResponse(
                        1L,
                        "Laptop",
                        "admin",
                        null,
                        null,
                        null
                );

        when(productService.getProductById(1L))
                .thenReturn(response);

        mockMvc.perform(
                        get("/api/v1/products/1")
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.productName").value("Laptop"));
    }
}
