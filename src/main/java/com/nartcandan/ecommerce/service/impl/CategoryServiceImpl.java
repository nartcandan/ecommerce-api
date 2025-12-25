package com.nartcandan.ecommerce.service.impl;

import com.nartcandan.ecommerce.domain.entities.Category;
import com.nartcandan.ecommerce.repository.CategoryRepository;
import com.nartcandan.ecommerce.service.CategoryService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    @Transactional
    public void deleteCategory(UUID id) {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()){
            if (!category.get().getProducts().isEmpty()){
                throw new IllegalStateException("Category has products associated with it");
            }
            categoryRepository.deleteById(id);
        }
        else {
            throw new IllegalArgumentException("There is no category with given id");
        }
    }

    @Override
    public Category getCategory(UUID id) {
        return categoryRepository.findByIdWithProductCount(id).orElseThrow(() ->
                new IllegalArgumentException("There is no category with given id: " + id));
    }
}
