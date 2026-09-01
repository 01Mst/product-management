package com.thulasimani.product_management.service;

import com.thulasimani.product_management.dto.request.ProductRequest;
import com.thulasimani.product_management.dto.response.ProductResponse;
import com.thulasimani.product_management.entity.Product;
import com.thulasimani.product_management.exception.ResourceNotFoundException;
import com.thulasimani.product_management.repository.ItemRepository;
import com.thulasimani.product_management.repository.ProductRepository;
import com.thulasimani.product_management.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {

        product = Product.builder()
                .id(1L)
                .productName("Laptop")
                .createdBy("admin")
                .modifiedBy("admin")
                .build();

        SecurityContextHolder.getContext().setAuthentication(
                new TestingAuthenticationToken(
                        "admin",
                        null,
                        "ROLE_ADMIN"
                )
        );
    }

    @Test
    void getProductById_shouldReturnProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        ProductResponse response =
                productService.getProductById(1L);

        assertThat(response).isNotNull();
        assertThat(response.id()).isEqualTo(1L);
        assertThat(response.productName()).isEqualTo("Laptop");

        verify(productRepository).findById(1L);
    }

    @Test
    void getProductById_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                productService.getProductById(1L)
        )
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository).findById(1L);
    }

    @Test
    void getAllProducts_shouldReturnPaginatedProducts() {

        PageRequest pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(product)));

        var result = productService.getAllProducts(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).productName())
                .isEqualTo("Laptop");

        verify(productRepository).findAll(pageable);
    }

    @Test
    void createProduct_shouldSaveProduct() {

        ProductRequest request =
                new ProductRequest("Mobile");

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> {
                    Product saved = invocation.getArgument(0);
                    saved.setId(1L);
                    return saved;
                });

        ProductResponse response =
                productService.createProduct(request);

        assertThat(response).isNotNull();
        assertThat(response.productName()).isEqualTo("Mobile");

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void deleteProduct_shouldDeleteProduct() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        productService.deleteProduct(1L);

        verify(productRepository).delete(product);
    }

    @Test
    void deleteProduct_shouldThrowException_whenProductDoesNotExist() {

        when(productRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                productService.deleteProduct(1L)
        )
                .isInstanceOf(ResourceNotFoundException.class);

        verify(productRepository, never())
                .delete(any(Product.class));
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }
}
