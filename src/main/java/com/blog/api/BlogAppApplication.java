package com.blog.api;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.api.config.AppConstants;
import com.blog.api.models.Role;
import com.blog.api.repositories.RoleRepository;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@SpringBootApplication
public class BlogAppApplication implements CommandLineRunner { 
    
    @Autowired
    private RoleRepository roleRepository;

    public static void main(String[] args) {
	SpringApplication.run(BlogAppApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
	return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {

	try {
	    Role admin = new Role();
	    admin.setId(AppConstants.ADMIN_ROLE_ID);
	    admin.setName("ADMIN");
	    admin.setDescription("has access to everything");

	    Role user = new Role();
	    user.setId(AppConstants.USER_ROLE_ID);
	    user.setName("USER");
	    user.setDescription("cannot perform CRUD operations");

	    List<Role> roles = List.of(admin, user);

	    roleRepository.saveAll(roles);
	} catch (Exception e) {
	    e.getStackTrace();
	}

    }

}
