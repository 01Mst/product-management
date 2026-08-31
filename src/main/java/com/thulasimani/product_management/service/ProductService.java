package com.thulasimani.product_management.service;

import com.thulasimani.product_management.dto.request.ProductRequest;
import com.thulasimani.product_management.dto.response.ProductResponse;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);

}
