package com.blog.api.payloads;

import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Integer id;
    @NotBlank
    @Size(min = 5, max = 50)
    private String name;
    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
	    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
    private String email;
    @NotBlank
    @Size(max = 50)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[#$@!%&*?])[A-Za-z\\d#$@!%&*?]{8,}$", message = "minimum 1 uppercase letter, "
	    + "minimum 1 lowercase letter, " + "minimum 1 special character, " + "minimum 1 number, "
	    + "minimum 8 characters ")
    private String password;
    @NotBlank
    @Size(min = 20, max = 50)
    private String about;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent
    private LocalDate dateOfBirth;
    
    @JsonIgnore
    public String getPassword()
    {
    	return this.password;
    }
    
    @JsonProperty
    public void setPassword(String password)
    {
    	this.password=password;
    }

}
