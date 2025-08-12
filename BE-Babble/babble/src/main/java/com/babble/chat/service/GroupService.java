package com.babble.chat.service;

import java.util.Set;

import com.babble.chat.model.GroupMaster;
import com.babble.chat.request.GroupMasterRequest;
import com.babble.chat.response.GroupResponse;

import jakarta.validation.Valid;

public interface GroupService {

	GroupMaster saveGroup(@Valid GroupMasterRequest groupRequest);

	Set<GroupResponse> getAllGroup(Long myNumber, Integer status);

}
