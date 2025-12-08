package com.nartcandan.ecommerce.service.impl;

import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.repository.CategoryRepository;
import com.nartcandan.ecommerce.service.CategoryService;
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
}
