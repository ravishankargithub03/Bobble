package com.babble.chat.service;

import java.util.List;
import java.util.Map;

import com.babble.chat.request.MessageMasterRequest;
import com.babble.chat.response.GroupResponse;

import jakarta.validation.Valid;

public interface MessageMasterService {

	Integer saveMessage(@Valid MessageMasterRequest request) throws Exception;

	List<Map<String, Object>> getOneToOneAllMessage(Long myNumber, Long contactNumber) throws Exception;

	List<Map<String, Object>> getGroupAllMessage(Long myNumber, Long groupId) throws Exception;

	List<GroupResponse> getAllMessageBy(Long myNumber);

}
