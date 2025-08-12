package com.babble.chat.service.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.babble.chat.common.Constants;
import com.babble.chat.common.Constants.RecordStatus;
import com.babble.chat.model.GroupMaster;
import com.babble.chat.model.MessageMaster;
import com.babble.chat.model.UserMaster;
import com.babble.chat.repository.MessageMasterRepository;
import com.babble.chat.repository.UserMasterRepository;
import com.babble.chat.request.MessageMasterRequest;
import com.babble.chat.response.GroupResponse;
import com.babble.chat.service.AESCryptoService;
import com.babble.chat.service.MessageMasterService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageMasterServiceImpl implements MessageMasterService {

	private MessageMasterRepository messageMasterRepository;
	
	private UserMasterRepository userMasterRepository;
	
    private AESCryptoService aesCryptoService;
    
//	====================================================
	
    @Override
	public Integer saveMessage(MessageMasterRequest request) throws Exception {
		Integer status = null;
		MessageMaster savedMessage = null;
		
		MessageMaster entity = new MessageMaster();
		entity.setContent(aesCryptoService.encrypt(request.getContent()));
		
		UserMaster sender = new UserMaster();
		sender.setId(request.getSenderId());
		entity.setSender(sender);
		
		if(request.getIsGroup()) {
			Set<GroupMaster> recipients = new HashSet<>();
			GroupMaster group = new GroupMaster();
			group.setId(request.getReceiverId().get(0));
			recipients.add(group);
			entity.setGroupRecipients(recipients);
		} else {
			Set<UserMaster> recipients = request.getReceiverId().stream().map(id -> {
				return userMasterRepository.getByPhoneNumberAndStatus(id, Constants.RecordStatus.ACTIVE); }).collect(Collectors.toSet());
			entity.setRecipients(recipients);
		}
		
		entity.setCreatedOn(LocalDateTime.now());
		entity.setStatus(RecordStatus.ACTIVE);
		savedMessage = messageMasterRepository.save(entity);
		
		if (savedMessage != null) {
			status = savedMessage.getStatus();
		}
		
		return status;
	}

//	================================================================
	
    @Override
	public List<Map<String, Object>> getOneToOneAllMessage(Long myNumber, Long contactNumber) throws Exception {
		List<Map<String, Object>> response = null;
		Set<Map<String, Object>> messages = null;
		
		messages = processMessages(messageMasterRepository.getMyMessages(myNumber, contactNumber, RecordStatus.DELETE), myNumber);

		if ((!messages.isEmpty())) {

			response = new ArrayList<>(messages);

			Collections.sort(response, new Comparator<Map<String, Object>>() {
				@Override
				public int compare(Map<String, Object> m1, Map<String, Object> m2) {
					Long messageId1 = (Long) m1.get("messageId"); // Adjust the key as needed
					Long messageId2 = (Long) m2.get("messageId"); // Adjust the key as needed
					return Long.compare(messageId1, messageId2);
				}
			});
		}
		
		return response;
	}
	
//	========================================================
	
	private Set<Map<String, Object>> processMessages(List<Map<String, Object>> messages, Long myNumber) throws Exception {
	    Set<Map<String, Object>> response = new HashSet<>();
	    if (messages != null && !messages.isEmpty()) {
	        for (Map<String, Object> entityObj : messages) {
	            Map<String, Object> responseObj = new HashMap<>();
	            responseObj.put("myContent", aesCryptoService.decrypt((String) entityObj.get("content")));
	            responseObj.put("messageId", entityObj.get("messageId"));
				if (myNumber.equals(entityObj.get("senderId")))
					responseObj.put("myChat", true);
				else
					responseObj.put("myChat", false);
				responseObj.put("time", ((Timestamp) entityObj.get("createdOn")).toLocalDateTime().toLocalTime());
				responseObj.put("date", ((Timestamp) entityObj.get("createdOn")).toLocalDateTime().toLocalDate());
	            response.add(responseObj);
	        }
	    }
	    return response;
	}
	
//	========================================================
	
	@Override
	public List<Map<String, Object>> getGroupAllMessage(Long myNumber, Long groupId) throws Exception {
		List<Map<String, Object>> response = null;
		Set<Map<String, Object>> messages = null;
		
		messages = processMessages(messageMasterRepository.getGroupMemberMessages(groupId, RecordStatus.DELETE), myNumber);
		
		if ((!messages.isEmpty())) {
			
			response = new ArrayList<>(messages);
			
			Collections.sort(response, new Comparator<Map<String, Object>>() {
			    @Override
			    public int compare(Map<String, Object> m1, Map<String, Object> m2) {
			        Long messageId1 = (Long) m1.get("messageId"); // Adjust the key as needed
			        Long messageId2 = (Long) m2.get("messageId"); // Adjust the key as needed
			        return Long.compare(messageId1, messageId2);
			    }
			});
		}
		
		return response;
	}

//	==========================================================
	
	@Override
	public List<GroupResponse> getAllMessageBy(Long myNumber) {
		List<GroupResponse> response = null;
		List<Map<String, Object>> groupDetailsList = null;
		
		groupDetailsList = messageMasterRepository.getMessageByDetailsList(myNumber);
		
		if (groupDetailsList != null && !groupDetailsList.isEmpty()) {
		    Set<Long> seenGroupIds = new HashSet<>();

			response = groupDetailsList.stream().filter(mapObject -> {
				Long groupId = (Long) mapObject.get("groupId");
				return groupId != null && seenGroupIds.add(groupId);
			}).map(mapObject -> {
				GroupResponse record = new GroupResponse();
				record.setGroupId((Long) mapObject.get("groupId"));
				record.setGroupCode((String) mapObject.get("groupCode"));
				record.setGroupName((String) mapObject.get("groupName"));
				record.setNarrative((String) mapObject.get("narrative"));
				record.setMasterId((Long) mapObject.get("masterId"));
				String firstName = (String) mapObject.get("masterFirstName");
				String lastName = (String) mapObject.get("masterLastName");
				record.setMasterName((firstName != null ? firstName : "") + " " + (lastName != null ? lastName : ""));
				record.setMasterNumber((Long) mapObject.get("masterPhoneNumber"));
				if (record.getGroupCode() != null)
					record.setIsGroup(true);
				else
					record.setIsGroup(false);
				return record;
			}).collect(Collectors.toList());
		}
		
		return response;
	}
	
}
