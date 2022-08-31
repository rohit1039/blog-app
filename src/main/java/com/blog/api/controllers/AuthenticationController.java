package com.blog.api.controllers;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.JWTAuthRequest;
import com.blog.api.payloads.JWTAuthResponse;
import com.blog.api.payloads.UserDTO;
import com.blog.api.security.BloggingUserDetails;
import com.blog.api.security.JwtTokenHelper;
import com.blog.api.services.UserService;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService detailsService;

	@Autowired
	private UserService userService;

	@Autowired
	private ModelMapper modelMapper;

	/**
	 * 
	 * @param userDTO
	 * @return
	 */
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerNewUser(@Valid @RequestBody UserDTO userDTO) {

		UserDTO registeredUser = userService.registerNewUser(userDTO);
		return new ResponseEntity<UserDTO>(registeredUser, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param jwtAuthRequest
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> login(@RequestBody JWTAuthRequest jwtAuthRequest) throws Exception {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword()));

		UserDetails userDetails = detailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		String token = jwtTokenHelper.generateToken(userDetails);
		JWTAuthResponse authResponse = new JWTAuthResponse();
		BloggingUserDetails bloggingUserDetails = (BloggingUserDetails) authentication.getPrincipal();
		authResponse.setToken(token);
		authResponse.setUser(this.modelMapper.map(bloggingUserDetails.getUser(),UserDTO.class));

		return new ResponseEntity<JWTAuthResponse>(authResponse, HttpStatus.OK);
	}
}
