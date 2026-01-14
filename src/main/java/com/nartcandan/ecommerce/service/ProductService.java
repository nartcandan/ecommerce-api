package com.nartcandan.ecommerce.service;
import com.nartcandan.ecommerce.domain.dtos.*;
import com.nartcandan.ecommerce.domain.entities.Product;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    PagedResponse<ProductListDto> getAllProducts(Pageable pageable);
    ProductDetailDto createProduct(CreateProductRequest request);
    ProductDetailDto getProductById(UUID id);
    ProductDetailDto updateProduct(UUID id, UpdateProductRequest request);
    void updateProductInventory(UUID id, UpdateProductInventoryRequest request);
    void updateStatus(UUID id, UpdateProductStatusRequest request);
    void deleteProduct(UUID id);
}
