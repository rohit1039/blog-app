package com.blog.api.services.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.api.config.AppConstants;
import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.models.Role;
import com.blog.api.models.User;
import com.blog.api.payloads.UserDTO;
import com.blog.api.repositories.RoleRepository;
import com.blog.api.repositories.UserRepository;
import com.blog.api.services.UserService;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private void encodePassword(User user) {
	String encodedPassword = passwordEncoder.encode(user.getPassword());
	user.setPassword(encodedPassword);
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO, Integer Id) {

	User user = userRepository.findById(Id).orElseThrow(() -> new ResourceNotFoundException("User not found!"));

	user.setName(userDTO.getName());
	user.setAbout(userDTO.getAbout());
	user.setDateOfBirth(userDTO.getDateOfBirth());
	user.setEmail(userDTO.getEmail());
	user.setPassword(userDTO.getPassword());

	User updatedUser = userRepository.save(user);

	return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public UserDTO getUserById(Integer Id) {
	User user = userRepository.findById(Id)
		.orElseThrow(() -> new ResourceNotFoundException("User doesn't exists with ID: " + Id));
	return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
	List<User> allUsers = userRepository.findAll();
	if (allUsers.isEmpty()) {
	    throw new ResourceNotFoundException("No users found!");
	}
	List<UserDTO> allDTOUsers = allUsers.stream().map(user -> modelMapper.map(user, UserDTO.class))
		.collect(Collectors.toList());
	return allDTOUsers;
    }

    @Override
    public UserDTO deleteUserById(Integer Id) {
	User user = userRepository.findById(Id)
		.orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + Id));
	userRepository.delete(user);
	return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public UserDTO registerNewUser(UserDTO userDTO) {
	User user = modelMapper.map(userDTO, User.class);
	encodePassword(user); // password encoded
	Role role = roleRepository.findById(AppConstants.USER_ROLE_ID).get();
	user.getRoles().add(role);

	User registeredUser = userRepository.save(user);

	return modelMapper.map(registeredUser, UserDTO.class);
    }

}
