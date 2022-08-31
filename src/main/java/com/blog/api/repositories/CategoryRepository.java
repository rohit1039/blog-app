package com.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.models.Category;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
