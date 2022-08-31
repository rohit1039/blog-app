package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.CategoryDTO;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface CategoryService {

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO);

    CategoryDTO getCategory(Integer categoryId);

    List<CategoryDTO> getAllCategories();

    CategoryDTO deleteCategory(Integer categoryId);

}
