package com.thulasimani.product_management.service;

import com.thulasimani.product_management.dto.request.ItemRequest;
import com.thulasimani.product_management.dto.request.ProductRequest;
import com.thulasimani.product_management.dto.response.ItemResponse;
import com.thulasimani.product_management.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    ProductResponse createProduct(ProductRequest request);
    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProducts(Pageable pageable);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    List<ItemResponse> getItemsByProductId(Long productId);

    ItemResponse createItem(Long productId, ItemRequest request);


}
