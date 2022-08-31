package com.blog.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.models.Category;
import com.blog.api.models.Post;
import com.blog.api.models.User;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByUser(User user);

    List<Post> findByCategory(Category category);

    List<Post> findByTitleContaining(String title);
}
