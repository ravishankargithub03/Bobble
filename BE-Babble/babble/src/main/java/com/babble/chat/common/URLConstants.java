package com.babble.chat.common;

public class URLConstants {

	public static class ContactURL{
		public static final String BASE_API = "/api/contact";
		public static final String SAVE_CONTACT = "/save";
		public static final String GET_ALL_CONTACT = "/get/all/{myNumber}";
		public static final String GET_ALL_CONTACT_REQUEST = "/request/get/all/{myNumber}";
		public static final String UPDATE_CONTACT_STATUS = "/request/status/{contactId}/{status}";
	}
	
	public static class GroupURL{
		public static final String BASE_API = "/api/group";
		public static final String SAVE_GROUP = "/save";
		public static final String GET_ALL_GROUP = "/get/all/{myNumber}/{status}";
		public static final String DELETE_CONTACT = "/request/status/{contactId}/{status}";
	}
	
	public static class MessageURL{
		public static final String BASE_API = "/api/message";
		public static final String SAVE_MESSAGE = "/save";
		public static final String GET_ALL_ONE_TO_ONE_MESSAGE = "/onetoone/get/all/{myNumber}/{contactNumber}";
		public static final String GET_ALL_GROUP_MESSAGE = "/group/get/all/{myNumber}/{groupId}";
		public static final String DELETE_CONTACT = "/request/status/{contactId}/{status}";
		public static final String GET_ALL_MESSAGE_BY = "/by/get/all/{myNumber}";
	}
	
	public static class UserURL{
		public static final String BASE_API = "/api/user";
		public static final String SAVE_USER = "/save";
		public static final String GET_ALL_CONTACT = "/request/get/all/{myNumber}/{status}";
		public static final String DELETE_CONTACT = "/request/status/{contactId}/{status}";
	}
}
