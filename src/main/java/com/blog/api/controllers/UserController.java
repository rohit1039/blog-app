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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.UserDTO;
import com.blog.api.services.UserService;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 
     * @param userDTO
     * @param userId
     * @return
     */
    @PutMapping("/update/user/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(@Valid @RequestBody UserDTO userDTO,
	    @PathVariable("id") Integer userId) {

	EntityModel<UserDTO> entityModel = EntityModel.of(userService.updateUser(userDTO, userId));
	WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
	entityModel.add(link.withRel("all-users"));

	return ResponseEntity.ok(entityModel);
    }

    /**
     * 
     * @param userId
     * @return
     */
    @GetMapping("/get/user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable("id") Integer userId) {

	UserDTO userDTO = userService.getUserById(userId);

	return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    /**
     * 
     * @return
     */
    @GetMapping("/getallusers")
    public ResponseEntity<List<UserDTO>> getAllUsers() {

	return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * 
     * @param Id
     * @return
     */
    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<EntityModel<UserDTO>> deleteUser(@PathVariable("id") Integer id) {

	EntityModel<UserDTO> model = EntityModel.of(userService.deleteUserById(id));
	WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).getAllUsers());
	model.add(link.withRel("all-users"));

	return ResponseEntity.ok(model);
    }

}
