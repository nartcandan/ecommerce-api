package com.nartcandan.ecommerce.controller;

import com.nartcandan.ecommerce.domain.dtos.CategoryDto;
import com.nartcandan.ecommerce.domain.dtos.CreateCategoryRequest;
import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.mapper.CategoryMapper;
import com.nartcandan.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @PostMapping
    public ResponseEntity<CategoryDto> addCategory(@RequestBody CreateCategoryRequest createCategoryRequest){
        Category category = categoryService.addCategory(categoryMapper.toEntity(createCategoryRequest));

        return new ResponseEntity<>(categoryMapper.toDto(category), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable UUID id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
