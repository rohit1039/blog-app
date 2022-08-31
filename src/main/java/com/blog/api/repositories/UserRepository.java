package com.blog.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.api.models.User;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);
}
