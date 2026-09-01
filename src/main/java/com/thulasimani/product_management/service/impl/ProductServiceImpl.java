package com.thulasimani.product_management.service.impl;

import com.thulasimani.product_management.dto.request.ItemRequest;
import com.thulasimani.product_management.dto.request.ProductRequest;
import com.thulasimani.product_management.dto.response.ItemResponse;
import com.thulasimani.product_management.dto.response.ProductResponse;
import com.thulasimani.product_management.entity.Item;
import com.thulasimani.product_management.entity.Product;
import com.thulasimani.product_management.exception.ResourceNotFoundException;
import com.thulasimani.product_management.repository.ItemRepository;
import com.thulasimani.product_management.repository.ProductRepository;
import com.thulasimani.product_management.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ItemRepository itemRepository;

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

    @Override
    @Transactional(readOnly = true)
    public Page<ProductResponse> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public ProductResponse updateProduct(Long id, ProductRequest request) {
        Product product=findProductById(id);
        product.setProductName(request.productName());
        product.setModifiedBy("System");
        product.setModifiedOn(LocalDateTime.now());
        Product updatedProduct=productRepository.save(product);
        return mapToResponse(updatedProduct);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product=findProductById(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ItemResponse> getItemsByProductId(Long productId) {
        findProductById(productId);
        return itemRepository.findByProductId(productId)
                .stream()
                .map(item -> new ItemResponse(item.getId(),item.getProduct().getId(),item.getQuantity()))
                .toList();
    }

    @Override
    public ItemResponse createItem(Long productId, ItemRequest request) {
        Product product=findProductById(productId);
        Item item= Item.builder()
                .product(product)
                .quantity(request.quantity())
                .build();

        Item savedItem=itemRepository.save(item);
        return new ItemResponse(savedItem.getId(), savedItem.getProduct().getId(), savedItem.getQuantity());
    }

    private Product findProductById(Long id){
        return productRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Product not found with id: " +id));
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
