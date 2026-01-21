package com.nartcandan.ecommerce.controller;

import com.nartcandan.ecommerce.domain.dtos.*;
import com.nartcandan.ecommerce.domain.enums.ProductStatus;
import com.nartcandan.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<PagedResponse<ProductListDto>> getProducts(
            @RequestParam(required = false) UUID categoryId,
            @RequestParam(required = false) ProductStatus status,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) Boolean inStockOnly,
            @RequestParam(required = false) String search,
            @PageableDefault(page = 0, size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        ProductFilter filter = ProductFilter.builder()
                .categoryId(categoryId)
                .status(status)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .inStockOnly(inStockOnly)
                .search(search)
                .build();

        PagedResponse<ProductListDto> response = productService.getProducts(filter, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ProductDetailDto> createProduct(@RequestBody @Valid CreateProductRequest request){
        ProductDetailDto productDto = productService.createProduct(request);
        return ResponseEntity.ok(productDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<ProductDetailDto> getProduct(@PathVariable UUID id){
        ProductDetailDto productDetailDto = productService.getProductById(id);
        return ResponseEntity.ok(productDetailDto);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<ProductDetailDto> updateProduct(@PathVariable UUID id, @RequestBody @Valid UpdateProductRequest request){
        ProductDetailDto productDetailDto = productService.updateProduct(id, request);
        return ResponseEntity.ok(productDetailDto);
    }

    @PatchMapping(path = "/{id}/inventory")
    public ResponseEntity<Void> updateInventory(@PathVariable UUID id, @Valid @RequestBody UpdateProductInventoryRequest request){
        productService.updateProductInventory(id,request);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(path ="/{id}/status")
    public ResponseEntity<Void> updateStatus(@PathVariable UUID id,
                                             @Valid @RequestBody UpdateProductStatusRequest request) {
        productService.updateStatus(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
