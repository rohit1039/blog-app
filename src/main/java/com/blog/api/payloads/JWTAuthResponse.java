package com.blog.api.payloads;


import lombok.Data;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Data
public class JWTAuthResponse {
	
	private UserDTO user;
    private String token;
}
