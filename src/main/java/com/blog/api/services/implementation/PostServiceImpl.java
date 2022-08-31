package com.blog.api.services.implementation;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.api.exceptions.ResourceNotFoundException;
import com.blog.api.models.Category;
import com.blog.api.models.Post;
import com.blog.api.models.User;
import com.blog.api.payloads.PostDTO;
import com.blog.api.payloads.PostResponse;
import com.blog.api.repositories.CategoryRepository;
import com.blog.api.repositories.PostRepository;
import com.blog.api.repositories.UserRepository;
import com.blog.api.services.PostService;

/**
 * 
 * @author - Rohit Parida
 * 
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDTO createPost(PostDTO postDTO, Integer categoryId, Integer userId) {

	User user = userRepository.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("As user doesn't exist, post cannot be created!"));
	Category category = categoryRepository.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category doesn't exist"));
	Post post = modelMapper.map(postDTO, Post.class);
	post.setCreatedTime(LocalDateTime.now());
	post.setImageName("default.png");
	post.setCategory(category);
	post.setUser(user);
	Post createdPost = postRepository.save(post);
	return modelMapper.map(createdPost, PostDTO.class);

    }

    @Override
    public PostDTO updatePost(Integer postId, PostDTO postDTO) {

	Post postInDB = postRepository.findById(postId)
		.orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
	postInDB.setTitle(postDTO.getTitle());
	postInDB.setContent(postDTO.getContent());
	postInDB.setImageName(postDTO.getImageName());
	postInDB.setCreatedTime(LocalDateTime.now());
	Post savedPost = postRepository.save(postInDB);
	PostDTO updatedPost = modelMapper.map(savedPost, PostDTO.class);

	return updatedPost;
    }

    @Override
    public PostResponse getAllPosts(Integer pageNum, Integer pageSize, String sortBy, String sortDir) {

	Sort sort = null;
	if (sortDir.equalsIgnoreCase("asc")) {
	    sort = Sort.by(sortBy).ascending();
	} else if (sortDir.equalsIgnoreCase("desc")) {
	    sort = Sort.by(sortBy).descending();
	}
	Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
	Page<Post> pagePost = postRepository.findAll(pageable);
	List<Post> posts = pagePost.getContent();

	List<PostDTO> postsDTO = posts.stream().map((post) -> modelMapper.map(post, PostDTO.class))
		.collect(Collectors.toList());

	PostResponse postResponse = new PostResponse();

	postResponse.setContent(postsDTO);
	postResponse.setPageNum(pagePost.getNumber());
	postResponse.setPageSize(pagePost.getSize());
	postResponse.setTotalElements(pagePost.getTotalElements());
	postResponse.setTotalPages(pagePost.getTotalPages());
	postResponse.setLastPage(pagePost.isLast());

	return postResponse;
    }

    // Get post by postId

    @Override
    public PostDTO getPostById(Integer postId) {
	Post post = postRepository.findById(postId)
		.orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
	return modelMapper.map(post, PostDTO.class);
    }

    // Get post by category

    @Override
    public List<PostDTO> getPostByCategory(Integer categoryId) {

	Category cat = categoryRepository.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("No post found with categoryId: " + categoryId));
	List<Post> posts = postRepository.findByCategory(cat);

	List<PostDTO> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
		.collect(Collectors.toList());

	return postDtos;

    }

    // Get post by user

    @Override
    public List<PostDTO> getPostByUser(Integer userId) {

	User user = userRepository.findById(userId)
		.orElseThrow(() -> new ResourceNotFoundException("No post found with userId: " + userId));
	List<Post> posts = postRepository.findByUser(user);

	List<PostDTO> postDTO = posts.stream().map((post) -> this.modelMapper.map(post, PostDTO.class))
		.collect(Collectors.toList());

	return postDTO;
    }

    @Override
    public PostDTO deletePostById(Integer postId) {
	Post postInDB = postRepository.findById(postId)
		.orElseThrow(() -> new ResourceNotFoundException("Post not found with ID: " + postId));
	postRepository.delete(postInDB);
	PostDTO mappedPost = modelMapper.map(postInDB, PostDTO.class);
	return mappedPost;
    }

    @Override
    public List<PostDTO> searchPostByKeyword(String keyword) {

	List<Post> searchResult = postRepository.findByTitleContaining(keyword);
	if (searchResult.isEmpty()) {
	    throw new ResourceNotFoundException("No post found with title: " + "'" + keyword + "'");
	}
	List<PostDTO> mappedResult = searchResult.stream().map((title) -> modelMapper.map(title, PostDTO.class))
		.collect(Collectors.toList());
	return mappedResult;
    }
}
