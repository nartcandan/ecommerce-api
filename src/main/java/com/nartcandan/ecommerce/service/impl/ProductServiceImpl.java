package com.nartcandan.ecommerce.service.impl;

import com.nartcandan.ecommerce.domain.dtos.*;
import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.domain.entities.Product;
import com.nartcandan.ecommerce.mapper.ProductMapper;
import com.nartcandan.ecommerce.repository.CategoryRepository;
import com.nartcandan.ecommerce.repository.ProductRepository;
import com.nartcandan.ecommerce.service.CategoryService;
import com.nartcandan.ecommerce.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Override
    public List<ProductListDto> getAllProducts() {
         return productMapper.toListDtos(productRepository.findAll());
    }

    @Override
    @Transactional
    public ProductDetailDto createProduct(CreateProductRequest request) {
        Optional<Category> category = categoryRepository.findById(request.getCategoryId());
        if (category.isEmpty()){
            throw new IllegalArgumentException("Category not found with given id in the product model");
        }
        Product product = productMapper.toEntity(request);
        product.setCategory(category.get());
        return productMapper.toDetailDto(productRepository.save(product));
    }

    @Override
    public ProductDetailDto getProductById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("Product is not found with given id"));
        return productMapper.toDetailDto(product);
    }

    @Override
    @Transactional
    public ProductDetailDto updateProduct(UUID id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Product that you are trying to update is not found"));
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() ->
                new IllegalArgumentException("Category that you are trying to update in the product model is not found"));
        productMapper.updateEntityFromDto(request, product);
        product.setCategory(category);
        Product update = productRepository.save(product);
        return productMapper.toDetailDto(update);
    }

    @Override
    @Transactional
    public void updateProductInventory(UUID productId, UpdateProductInventoryRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product is not found"));

        int current = product.getQuantity();
        int delta = request.getAmount();

        switch (request.getOperation()) {
            case INCREASE -> product.setQuantity(current + delta);
            case DECREASE -> {
                int newQuantity = current - delta;
                if (newQuantity < 0) {
                    throw new IllegalArgumentException("Stock can not be negative");
                }
                product.setQuantity(newQuantity);
            }
        }
        productRepository.save(product);
    }

    @Override
    @Transactional
    public void updateStatus(UUID productId, UpdateProductStatusRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product is not found"));

        product.setStatus(request.getStatus());
        productRepository.save(product);
    }
}
