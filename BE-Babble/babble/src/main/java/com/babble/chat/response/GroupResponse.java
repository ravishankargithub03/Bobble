package com.babble.chat.response;

import lombok.Data;

@Data
public class GroupResponse {

	private Long groupId;
	
	private String groupCode;
	
	private String groupName;
	
	private String narrative;
	
	private Long masterId;
	
	private String masterName;
	
	private Long masterNumber;
	
	private Boolean isGroup;
	
}
