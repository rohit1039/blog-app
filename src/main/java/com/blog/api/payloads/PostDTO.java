package com.blog.api.payloads;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {

    private Integer postId;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private String imageName;
    private LocalDateTime createdTime;
    @Valid
    private UserDTO user;
    @Valid
    private CategoryDTO category;
    private Set<CommentDTO> comments = new HashSet<>();

}