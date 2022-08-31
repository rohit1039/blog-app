package com.blog.api.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.api.config.AppConstants;
import com.blog.api.exceptions.ExceptionInApiResponse;
import com.blog.api.payloads.PostDTO;
import com.blog.api.payloads.PostResponse;
import com.blog.api.services.ImageFileService;
import com.blog.api.services.PostService;
//import com.blog.api.validations.ImageFileValidator;
import com.blog.api.validations.ImageFileValidator;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@RestController
@RequestMapping("/api/v1")
@CrossOrigin("*")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private ImageFileService imageFileService;

	@Autowired
	private ImageFileValidator imageFileValidator;

	@Value("${project.image}")
	private String imagePath;

	/**
	 * 
	 * @param postDTO
	 * @param categoryId
	 * @param userId
	 * @return
	 */
	@PostMapping("/save/post/category/{categoryId}/user/{userId}")
	public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer categoryId,
			@PathVariable Integer userId) {

		PostDTO createdPost = postService.createPost(postDTO, categoryId, userId);
		return new ResponseEntity<PostDTO>(createdPost, HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param postDTO
	 * @param postId
	 * @return
	 */
	@PutMapping("/update/post/{postId}")
	public ResponseEntity<PostDTO> updatePost(@Valid @RequestBody PostDTO postDTO, @PathVariable Integer postId) {

		PostDTO updatedPost = postService.updatePost(postId, postDTO);
		return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
	}

	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	@GetMapping("/get/posts") // localhost:9000/get/posts?pageNum=1&pageSize=5&sortBy=postId&sortDir=asc
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(defaultValue = AppConstants.PAGE_NUMBER) Integer pageNum,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
			@RequestParam(defaultValue = AppConstants.SORT_BY) String sortBy,
			@RequestParam(defaultValue = AppConstants.SORT_DIR) String sortDir) {
		PostResponse response = postService.getAllPosts(pageNum, pageSize, sortBy, sortDir);
		return ResponseEntity.ok(response);
	}

	// search post by title

	/**
	 * 
	 * @param title
	 * @return
	 */
	@GetMapping("/search/post/{title}")
	public ResponseEntity<List<PostDTO>> getSearchResults(@PathVariable("title") String title) {
		return ResponseEntity.ok(postService.searchPostByKeyword(title));
	}

	// Get post by postId

	/**
	 * 
	 * @param postId
	 * @return
	 */
	@GetMapping("/get/post/{postId}")
	public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId) {
		return ResponseEntity.ok(postService.getPostById(postId));
	}

	// image upload

	/**
	 * 
	 * @param postId
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@PostMapping("post/upload/image/{postId}")
	public ResponseEntity<?> uploadImage(@PathVariable Integer postId, @RequestParam("image") MultipartFile file)
			throws IOException {

		if (imageFileValidator.checkImage(file)) {
			PostDTO postDTO = postService.getPostById(postId);
			String fileName = imageFileService.uploadImage(imagePath, file);
			postDTO.setImageName(fileName);
			PostDTO updatedPost = postService.updatePost(postId, postDTO);

			return new ResponseEntity<PostDTO>(updatedPost, HttpStatus.OK);
		}
		return new ResponseEntity<ExceptionInApiResponse>(
				new ExceptionInApiResponse(LocalDateTime.now(), "It is not an image!", "Please upload a image file"),
				HttpStatus.BAD_REQUEST);
	}

	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, HttpServletResponse response)
			throws IOException {

		InputStream resource = this.imageFileService.getResource(imagePath, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());

	}

	// Get post by category

	/**
	 * 
	 * @param categoryId
	 * @param pageNum
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	@GetMapping("/get/posts/category/{categoryId}")
	public ResponseEntity<List<PostDTO>> getPostsByCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(postService.getPostByCategory(categoryId));
	}

	// Get post by user

	/**
	 * 
	 * @param userId
	 * @param pageNum
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	@GetMapping("/get/posts/user/{userId}")
	public ResponseEntity<List<PostDTO>> getPostsByUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(postService.getPostByUser(userId));
	}

	// delete post

	/**
	 * 
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/delete/post/{postId}")
	public ResponseEntity<PostDTO> deletePost(@PathVariable Integer postId) {
		PostDTO deletedPost = postService.deletePostById(postId);
		return ResponseEntity.ok(deletedPost);
	}

}
