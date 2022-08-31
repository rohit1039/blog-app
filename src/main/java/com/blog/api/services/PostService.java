package com.blog.api.services;

import java.util.List;

import com.blog.api.payloads.PostDTO;
import com.blog.api.payloads.PostResponse;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public interface PostService {

    PostDTO createPost(PostDTO postDTO, Integer categoryId, Integer userId);

    PostDTO updatePost(Integer postId, PostDTO postDTO);

    PostResponse getAllPosts(Integer pageNum, Integer pageSize, String sortBy, String sortDir);

    PostDTO getPostById(Integer postId);

    List<PostDTO> getPostByCategory(Integer categoryId);

    List<PostDTO> getPostByUser(Integer userID);

    List<PostDTO> searchPostByKeyword(String keyword);

    PostDTO deletePostById(Integer postId);

}
