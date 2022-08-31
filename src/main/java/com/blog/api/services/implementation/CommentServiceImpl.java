package com.blog.api.services.implementation;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.models.Comment;
import com.blog.api.models.Post;
import com.blog.api.models.User;
import com.blog.api.payloads.CommentDTO;
import com.blog.api.repositories.CommentRepository;
import com.blog.api.repositories.PostRepository;
import com.blog.api.repositories.UserRepository;
import com.blog.api.services.CommentService;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId, Integer userId) {

	User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found!"));
	Post post = postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post not found!"));

	Comment savedComment = modelMapper.map(commentDTO, Comment.class);
	savedComment.setPost(post);
	savedComment.setUser(user);
	commentRepository.save(savedComment);

	return modelMapper.map(savedComment, CommentDTO.class);
    }

    @Override
    public CommentDTO updateComment(Integer commentId, CommentDTO commentDTO) {

	Comment comment = commentRepository.findById(commentId)
		.orElseThrow(() -> new ResourceNotFoundException("Comment not found with ID: " + commentId));
	comment.setContent(commentDTO.getContent());
	Comment updatedComment = commentRepository.save(comment);

	return modelMapper.map(updatedComment, CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {

	Comment comment = commentRepository.findById(commentId).orElseThrow(()->new ResourceNotFoundException("Comment with ID: "+commentId+" not found!"));
	commentRepository.delete(comment);
	
    }

}
