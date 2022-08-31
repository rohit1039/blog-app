package com.blog.api.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@RestController
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionInApiResponse> handleResourceNotFoundException(ResourceNotFoundException ex,
	    WebRequest request) {

	ExceptionInApiResponse exceptionInApiResponse = new ExceptionInApiResponse(LocalDateTime.now(), ex.getMessage(),
		request.getDescription(false));
	return new ResponseEntity<ExceptionInApiResponse>(exceptionInApiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionInApiResponse> handleServerErrorException(Exception ex, WebRequest request) {

	ExceptionInApiResponse exceptionInApiResponse = new ExceptionInApiResponse(LocalDateTime.now(), ex.getMessage(),
		request.getDescription(false));
	return new ResponseEntity<ExceptionInApiResponse>(exceptionInApiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ExceptionInApiResponse> handleBadCredentialException(Exception ex, WebRequest request) {
	ExceptionInApiResponse exceptionInApiResponse = new ExceptionInApiResponse(LocalDateTime.now(), ex.getMessage(),
		request.getDescription(false));
	return new ResponseEntity<ExceptionInApiResponse>(exceptionInApiResponse, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
	    HttpHeaders headers, HttpStatus status, WebRequest request) {

	final Map<String, String> errors = new HashMap<String, String>();

	ex.getBindingResult().getFieldErrors().forEach((error) -> {

	    errors.put(error.getField(), error.getDefaultMessage());

	});

	ex.getBindingResult().getGlobalErrors().forEach((errorGlobal) -> {

	    errors.put(((FieldError) errorGlobal).getField(), errorGlobal.getDefaultMessage());

	});
	return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }
}
