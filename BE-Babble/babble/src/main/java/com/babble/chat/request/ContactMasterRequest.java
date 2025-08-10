package com.babble.chat.request;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Validated
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContactMasterRequest {

	@NotNull
	private Long myId;

	@NotNull
	private Long memberNumber;

}
