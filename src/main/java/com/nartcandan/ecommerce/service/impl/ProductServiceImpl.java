package com.nartcandan.ecommerce.service.impl;

import com.nartcandan.ecommerce.domain.dtos.CreateProductRequest;
import com.nartcandan.ecommerce.domain.dtos.ProductDetailDto;
import com.nartcandan.ecommerce.domain.dtos.ProductListDto;
import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.domain.entities.Product;
import com.nartcandan.ecommerce.mapper.ProductMapper;
import com.nartcandan.ecommerce.repository.CategoryRepository;
import com.nartcandan.ecommerce.repository.ProductRepository;
import com.nartcandan.ecommerce.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
}
