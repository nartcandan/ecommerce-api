package com.nartcandan.ecommerce.controller;

import com.nartcandan.ecommerce.domain.dtos.CategoryDto;
import com.nartcandan.ecommerce.mapper.CategoryMapper;
import com.nartcandan.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(){
        List<CategoryDto> list = categoryService.getAllCategories().stream().map(categoryMapper::toDto).toList();
        return ResponseEntity.ok(list);
    }

}
