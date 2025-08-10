package com.babble.chat.response;

import lombok.Data;

@Data
public class ContactResponse {

	private Long id;
	
	private Long memberId;
	
	private  String memberName;
	
	private Long memberNumber;
	
	private String memberEmail;
	
	private Boolean isGroup;
	
}
