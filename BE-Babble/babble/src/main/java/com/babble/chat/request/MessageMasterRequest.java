package com.babble.chat.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageMasterRequest {

	private Long messageId;

	@NotNull(message = "Sender Id Must be Needed")
	private Long senderId;
	
	@NotNull(message = "Receiver Id Must be Needed")
	private List<Long> receiverId;
	
	@NotBlank(message = "Content Must be Needed")
	private String content;
	
	@NotNull(message = "Is Group Value Must be Needed")
	private Boolean isGroup;
}
