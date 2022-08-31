package com.blog.api.exceptions;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ResourceNotFoundException(String msg) {
	super(msg);
    }

}
