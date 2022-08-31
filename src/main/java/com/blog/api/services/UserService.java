package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.UserDTO;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface UserService {

    public UserDTO registerNewUser(UserDTO userDTO);

    public UserDTO updateUser(UserDTO userDTO, Integer Id);

    public UserDTO getUserById(Integer Id);

    public List<UserDTO> getAllUsers();

    public UserDTO deleteUserById(Integer Id);

}
