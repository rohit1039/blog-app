package com.blog.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.models.User;
import com.blog.api.repositories.UserRepository;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Service
public class BloggingUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	User user = userRepository.findByEmail(username)
		.orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

	return new BloggingUserDetails(user);

    }

}
