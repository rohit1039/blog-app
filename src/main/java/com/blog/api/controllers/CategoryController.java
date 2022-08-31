package com.blog.api.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.CategoryDTO;
import com.blog.api.services.CategoryService;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@RestController
@RequestMapping("/api/v1")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save/category")
    public ResponseEntity<EntityModel<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {

	EntityModel<CategoryDTO> entityModel = EntityModel.of(categoryService.createCategory(categoryDTO));
	WebMvcLinkBuilder linkBuilder = linkTo(methodOn(this.getClass()).getAllCategories());
	entityModel.add(linkBuilder.withRel("all-categories"));

	return new ResponseEntity<EntityModel<CategoryDTO>>(entityModel, HttpStatus.CREATED);
    }

    @PutMapping("/update/category/{id}")
    public ResponseEntity<EntityModel<CategoryDTO>> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
	    @PathVariable("id") Integer categoryId) {

	EntityModel<CategoryDTO> entityModel = EntityModel.of(categoryService.updateCategory(categoryId, categoryDTO));
	WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllCategories());
	entityModel.add(link.withRel("all-categories"));

	return ResponseEntity.ok(entityModel);
    }

    @GetMapping("/getallcategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {

	List<CategoryDTO> allCategories = categoryService.getAllCategories();

	return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/get/category/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable("id") Integer categoryId) {

	return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }

    @DeleteMapping("/delete/category/{id}")
    public ResponseEntity<EntityModel<CategoryDTO>> deleteCategory(@PathVariable("id") Integer categoryId) {

	EntityModel<CategoryDTO> model = EntityModel.of(categoryService.deleteCategory(categoryId));
	WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllCategories());
	model.add(link.withRel("all-categories"));

	return ResponseEntity.ok(model);
    }

}
