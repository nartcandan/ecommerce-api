package com.nartcandan.ecommerce.controller;

import com.nartcandan.ecommerce.domain.dtos.CreateProductRequest;
import com.nartcandan.ecommerce.domain.dtos.ProductDetailDto;
import com.nartcandan.ecommerce.domain.dtos.ProductListDto;
import com.nartcandan.ecommerce.mapper.ProductMapper;
import com.nartcandan.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;
    
    @GetMapping
    public ResponseEntity<List<ProductListDto>> getAllProducts(){
        List<ProductListDto> listDtos = productService.getAllProducts();
        return ResponseEntity.ok(listDtos);
    }

    @PostMapping
    public ResponseEntity<ProductDetailDto> createProduct(@RequestBody @Valid CreateProductRequest request){
        ProductDetailDto productDto = productService.createProduct(request);
        return ResponseEntity.ok(productDto);
    }
}
