package com.blog.api.payloads;

import lombok.Getter;
import lombok.Setter;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Getter
@Setter
public class CommentDTO {

    private Integer commentId;
    private String content;
}
