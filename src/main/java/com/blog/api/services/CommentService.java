package com.blog.api.services;

import com.blog.api.payloads.CommentDTO;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface CommentService {
    
    CommentDTO createComment(CommentDTO commentDTO,Integer postId,Integer userId);
    
    CommentDTO updateComment(Integer commentId,CommentDTO commentDTO);
    
    // To fetch the comment I have used postRepository in Implementation
    
    void deleteComment(Integer commentId);
    
    

}
