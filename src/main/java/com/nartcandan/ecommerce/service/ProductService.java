package com.nartcandan.ecommerce.service;
import com.nartcandan.ecommerce.domain.dtos.CreateProductRequest;
import com.nartcandan.ecommerce.domain.dtos.ProductDetailDto;
import com.nartcandan.ecommerce.domain.dtos.ProductListDto;
import com.nartcandan.ecommerce.domain.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductListDto> getAllProducts();
    ProductDetailDto createProduct(CreateProductRequest request);
    ProductDetailDto getProductById(UUID id);
}
