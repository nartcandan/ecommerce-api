package com.nartcandan.ecommerce.service.impl;

import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.repository.CategoryRepository;
import com.nartcandan.ecommerce.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories(){
        return categoryRepository.findAllWithProductCount();
    }

    @Override
    @Transactional
    public Category addCategory(Category category) {
        if (categoryRepository.existsByNameIgnoreCase(category.getName())){
            throw new IllegalArgumentException("Category already exist with name: "+category.getName());
        }
        return categoryRepository.save(category);
    }
}
