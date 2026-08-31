package com.thulasimani.product_management.service.impl;

import com.thulasimani.product_management.dto.request.ProductRequest;
import com.thulasimani.product_management.dto.response.ProductResponse;
import com.thulasimani.product_management.entity.Product;
import com.thulasimani.product_management.exception.ResourceNotFoundException;
import com.thulasimani.product_management.repository.ProductRepository;
import com.thulasimani.product_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest request) {
        Product product=Product. builder()
                .productName(request.productName())
                .createdBy("System")
                .createdOn(LocalDateTime.now())
                .build();

        Product savedProduct=productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        Product product=productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id: "+id));
        return mapToResponse(product);
    }

    private ProductResponse mapToResponse(Product product){
        return new ProductResponse(
                product.getId(),
                product.getProductName(),
                product.getCreatedBy(),
                product.getCreatedOn(),
                product.getModifiedBy(),
                product.getModifiedOn()
        );
    }

}
