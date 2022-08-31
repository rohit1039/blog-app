package com.blog.api.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.models.Category;
import com.blog.api.payloads.CategoryDTO;
import com.blog.api.repositories.CategoryRepository;
import com.blog.api.services.CategoryService;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {

	Category category = modelMapper.map(categoryDTO, Category.class);
	Category savedCategory = categoryRepository.save(category);
	return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Integer categoryId, CategoryDTO categoryDTO) {

	Category categoryInDB = categoryRepository.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category not found!"));
	categoryInDB.setCategoryTitle(categoryDTO.getCategoryTitle());
	categoryInDB.setCategoryDescription(categoryDTO.getCategoryDescription());
	Category updatedCategory = categoryRepository.save(categoryInDB);
	return modelMapper.map(updatedCategory, CategoryDTO.class);

    }

    @Override
    public CategoryDTO getCategory(Integer categoryId) {
	return modelMapper.map(
		categoryRepository.findById(categoryId)
			.orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId)),
		CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
	List<Category> allCategoriesInDB = categoryRepository.findAll();
	if (allCategoriesInDB.isEmpty()) {
	    throw new ResourceNotFoundException("No categories found!");
	}
	List<CategoryDTO> allCategoriesInDTO = allCategoriesInDB.stream()
		.map((categories) -> modelMapper.map(categories, CategoryDTO.class)).collect(Collectors.toList());
	return allCategoriesInDTO;
    }

    @Override
    public CategoryDTO deleteCategory(Integer categoryId) {
	Category categoryInDB = categoryRepository.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category not found with ID: " + categoryId));
	categoryRepository.delete(categoryInDB);
	CategoryDTO mappedCategory = modelMapper.map(categoryInDB, CategoryDTO.class);
	return mappedCategory;

    }
}
