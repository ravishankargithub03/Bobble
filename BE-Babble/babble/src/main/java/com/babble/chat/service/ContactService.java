package com.babble.chat.service;

import java.util.Set;

import com.babble.chat.model.ContactMaster;
import com.babble.chat.request.ContactMasterRequest;
import com.babble.chat.response.ContactResponse;

import jakarta.validation.Valid;

public interface ContactService {

	ContactMaster saveContact(@Valid ContactMasterRequest request);

	Set<ContactResponse> getAllContact(Long myNumber);

	Set<ContactResponse> getAllContactRequest(Long myNumber);

	String updateRequestStatus(Long contactId, Integer status);
	
}
