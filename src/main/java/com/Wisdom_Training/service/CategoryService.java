package com.Wisdom_Training.service;

import com.Wisdom_Training.dto.request.CategoryDTO;
import com.Wisdom_Training.dto.request.CategoryDTO;
import com.Wisdom_Training.dto.respone.CategoryResponse;
import com.Wisdom_Training.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(CategoryDTO categoryDTO);

    CategoryResponse todo(Category category);

    List<Category> getAll();

    Category getCategoryById(int categoryId);

    Category updateCategory(int categoryId, CategoryDTO categoryDTO);

    void deleteCategory(int categoryId);
}
