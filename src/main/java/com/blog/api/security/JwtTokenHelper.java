package com.blog.api.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.blog.api.config.AppConstants;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author - Rohit Parida
 *
 * @year - 2022
 */
@Component
public class JwtTokenHelper {

    @Value("${app.jwt.secret}")
    private String secretKey;

    /**
     * 
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
	return getClaimFromToken(token, Claims::getSubject);
    }

    /**
     * 
     * @param token
     * @return
     */
    public Date getExpirationDateFromToken(String token) {
	return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * 
     * @param <T>
     * @param token
     * @param claimsResolver
     * @return
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
	final Claims claims = getAllClaimsFromToken(token);
	return claimsResolver.apply(claims);
    }

    /**
     * @param token
     * @return
     */
    private Claims getAllClaimsFromToken(String token) {
	return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * 
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
	final Date expiration = getExpirationDateFromToken(token);
	return expiration.before(new Date());
    }

    /**
     * 
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
	Map<String, Object> claims = new HashMap<>();
	return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * @param claims
     * @param username
     * @return 
     * Sign the JWT with HS512 algorithm and secret key
     */
    private String doGenerateToken(Map<String, Object> claims, String username) {

	return Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(new Date(System.currentTimeMillis()))
		.setExpiration(new Date(System.currentTimeMillis() + 1000 * AppConstants.JWT_TOKEN_VALIDITY))
		.signWith(SignatureAlgorithm.HS512, secretKey).compact();
    }

    /**
     * 
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
	final String username = getUsernameFromToken(token);
	return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
