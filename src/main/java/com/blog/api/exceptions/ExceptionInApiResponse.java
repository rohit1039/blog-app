package com.blog.api.exceptions;

import java.time.LocalDateTime;

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
public class ExceptionInApiResponse {

    private LocalDateTime timestamp;
    private String message;
    private String description;

}
