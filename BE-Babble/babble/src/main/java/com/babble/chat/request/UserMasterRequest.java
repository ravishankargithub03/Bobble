package com.babble.chat.request;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserMasterRequest {

	private Long id;
	
	@NotBlank(message = "First name must not be blank")
	private String firstName;
	
	@NotBlank(message = "Last name must not be blank")
	private String lastName;
	
	private String email;
	
	@NotNull(message = "Phone number must not be null")
	private Long phoneNumber;
}
