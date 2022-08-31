package com.blog.api.config;

/**
 * 
 * @author - Rohit Parida
 *
 * @year - 2022
 */
public class AppConstants {

    // Paging and Sorting

    public static final String PAGE_NUMBER = "0";
    public static final String PAGE_SIZE = "5";
    public static final String SORT_BY = "postId";
    public static final String SORT_DIR = "asc";

    // JWT (Json Web Token)

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * (long) 60;

    // USER role_id and ADMIN role_id

    public static final Integer USER_ROLE_ID = 502;
    public static final Integer ADMIN_ROLE_ID = 501;
    
    // publicly accessible URLs
    
    public static final String[] PUBLIC_URLS = { "/v3/api-docs", "/v2/api-docs", "/api/v1/auth/**", "/swagger-ui/**",
			"/swagger-resources/**", "/webjars/**" };

}
