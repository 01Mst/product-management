package com.thulasimani.product_management.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thulasimani.product_management.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@ActiveProfiles("test")
class ProductIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void applicationContext_shouldLoad() {

        assertThat(productRepository).isNotNull();
        assertThat(objectMapper).isNotNull();
    }
}
