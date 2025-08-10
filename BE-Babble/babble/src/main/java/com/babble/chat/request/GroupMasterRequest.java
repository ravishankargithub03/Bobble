package com.babble.chat.request;

import java.util.List;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupMasterRequest {

	private Long id;
	
	@NotBlank
	@Size(min = 2, max = 7)
	private String code;
	
	@NotBlank
	@Size(min = 3, max = 25)
	private String name;
	
	private String narrative;
	
	@NotNull
	private Long masterId;
	
	@NotNull
	private List<Long> memberIds;
}
