package com.blog.api.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.api.payloads.CommentDTO;
import com.blog.api.services.CommentService;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@RestController
@RequestMapping("/api/v1")
public class CommentController {
    
    @Autowired
    private CommentService commentService;
    
    @PostMapping("/comment/post/{postId}/user/{userId}")
    public ResponseEntity<CommentDTO> createComment(@Valid @RequestBody CommentDTO commentDTO,@PathVariable Integer postId,@PathVariable Integer userId)
    {
	return new ResponseEntity<CommentDTO>(commentService.createComment(commentDTO, postId, userId),HttpStatus.CREATED);
    }

}
