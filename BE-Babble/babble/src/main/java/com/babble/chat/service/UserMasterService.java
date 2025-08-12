package com.babble.chat.service;

import com.babble.chat.exception.ChatExceptionService;
import com.babble.chat.model.UserMaster;
import com.babble.chat.request.UserMasterRequest;

import jakarta.validation.Valid;

public interface UserMasterService {

	UserMaster saveUser(@Valid UserMasterRequest userRequest) throws ChatExceptionService;

}
