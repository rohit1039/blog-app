package com.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.models.Comment;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface CommentRepository extends JpaRepository<Comment, Integer>{

}
