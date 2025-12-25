package com.nartcandan.ecommerce.service;

import com.nartcandan.ecommerce.domain.entities.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<Category> getAllCategories();
    Category addCategory(Category category);
    void deleteCategory(UUID id);
}
