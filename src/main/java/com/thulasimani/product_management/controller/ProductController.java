package com.thulasimani.product_management.controller;

import com.thulasimani.product_management.dto.request.ItemRequest;
import com.thulasimani.product_management.dto.request.ProductRequest;
import com.thulasimani.product_management.dto.response.ItemResponse;
import com.thulasimani.product_management.dto.response.ProductResponse;
import com.thulasimani.product_management.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Product management APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(
            summary = "Create product",
            description = "Creates a new product. ADMIN role required."
    )
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest){
        ProductResponse response=productService.createProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get product by ID",
            description = "Returns a single product using its ID"
    )
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Returns a paginated list of products"
    )
    public ResponseEntity<Page<ProductResponse>> getAllProducts(Pageable pageable){
            return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Update product",
            description = "Updates an existing product. ADMIN role required."
    )
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest){
        return ResponseEntity.ok(productService.updateProduct(id, productRequest));
    }

    @DeleteMapping
    @Operation(
            summary = "Delete product",
            description = "Deletes a product. ADMIN role required."
    )
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/items")
    @Operation(
            summary = "Get product items",
            description = "Returns all items belonging to a product"
    )
    public ResponseEntity<List<ItemResponse>> getItemsByProductId(@PathVariable Long id){
        return ResponseEntity.ok(productService.getItemsByProductId(id));
    }

    @PostMapping("{id}/items")
    @Operation(
            summary = "Create product item",
            description = "Creates an item for a product"
    )
    public ResponseEntity<ItemResponse> createItem(@PathVariable Long id, @Valid @RequestBody ItemRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createItem(id, request));
    }

}
