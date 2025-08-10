package com.babble.chat.controller;

import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.babble.chat.common.Constants.ResponseMessages;
import com.babble.chat.common.Constants.ResponseStatus;
import com.babble.chat.common.URLConstants;
import com.babble.chat.exception.ChatExceptionService;
import com.babble.chat.model.ContactMaster;
import com.babble.chat.model.UserMaster;
import com.babble.chat.request.ContactMasterRequest;
import com.babble.chat.response.ContactResponse;
import com.babble.chat.response.Response;
import com.babble.chat.service.ContactService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@Tag(name = "contact")
@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(URLConstants.ContactURL.BASE_API)
public class ContactController extends BaseController{

	private ContactService contactService;
	
//	========================= Add Contact =========================
	
	@Operation(summary = "Add Contact", 
	           description = "This service is used for adding contact.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Contact added successfully!", 
	                 content = @Content(schema = @Schema(implementation = UserMaster.class))),
	    @ApiResponse(responseCode = "422", 
	    			 description = "Invalid input provided!", 
	                 content = @Content(schema = @Schema(implementation = UserMaster.class))),
	    @ApiResponse(responseCode = "10025", 
  			 		 description = "Data saving error!", 
  			 		 content = @Content(schema = @Schema(implementation = UserMaster.class))),
	    @ApiResponse(responseCode = "500", 
  			 		 description = "Internal server error!", 
  			 		 content = @Content(schema = @Schema(implementation = UserMaster.class)))
	})
	@PostMapping(URLConstants.ContactURL.SAVE_CONTACT)
	public ResponseEntity<Response> saveContact(@Valid @RequestBody ContactMasterRequest request) throws ChatExceptionService {
		Response response = null;

		ContactMaster contact = contactService.saveContact(request);

		if (contact != null) {
			response = new Response(ResponseStatus.OK, ResponseMessages.ADD_MESSAGE, contact);
		} else {
			response = new Response(ResponseStatus.INTERNAL_SERVER_ERROR, ResponseMessages.UNABLE_TO_ADD_MESSAGE, null);
		}
		
		return getOKResponseEntity(response);
	}
	
//	========================= Get All Contact =========================
	
	@Operation(summary = "Get All Contact", 
	           description = "This service is used for fetching all contact based on their status and user.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Contact fetched successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "204", 
	                 description = "No request found", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})	
	@GetMapping(URLConstants.ContactURL.GET_ALL_CONTACT)
	public ResponseEntity<Response> getAllContact(@PathVariable Long myNumber){
		Response response = null;
		Set<ContactResponse> contactResponse = null;
		
		contactResponse = contactService.getAllContact(myNumber);

		if (contactResponse != null) {
			response = new Response(ResponseStatus.OK, ResponseMessages.FETCHED_MESSAGE, contactResponse);
		} else {
			response = new Response(ResponseStatus.NO_CONTENT, ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}
		return getOKResponseEntity(response);
	}
	
//	========================== Get All Contact Request ============================
	
	@Operation(summary = "Get All Contact Request", 
	           description = "This service is used for fetching all contact request based on their status and user.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Contact fetched successfully", 
	                 content = @Content(schema = @Schema(implementation = Response.class))),
	    @ApiResponse(responseCode = "204", 
	                 description = "No request found", 
	                 content = @Content(schema = @Schema(implementation = Response.class)))
	})	
	@GetMapping(URLConstants.ContactURL.GET_ALL_CONTACT_REQUEST)
	public ResponseEntity<Response> getAllContactRequest(@PathVariable Long myNumber){
		Response response = null;
		Set<ContactResponse> contactResponse = null;
		
		contactResponse = contactService.getAllContactRequest(myNumber);

		if (contactResponse != null) {
			response = new Response(ResponseStatus.OK, ResponseMessages.FETCHED_MESSAGE, contactResponse);
		} else {
			response = new Response(ResponseStatus.NO_CONTENT, ResponseMessages.DATA_NOT_AVAILABLE_MESSAGE, null);
		}
		return getOKResponseEntity(response);
	}
	
//	========================== Update Contact Status ===================================
	
	@Operation(summary = "Update Contact Status", 
	           description = "This service is used for updating contact status.")
	@ApiResponses({
	    @ApiResponse(responseCode = "200", 
	                 description = "Contact updated successfully!", 
	                 content = @Content(schema = @Schema(implementation = UserMaster.class))),
	    @ApiResponse(responseCode = "422", 
	    			 description = "Invalid input provided!", 
	                 content = @Content(schema = @Schema(implementation = UserMaster.class))),
	    @ApiResponse(responseCode = "10025", 
			 		 description = "Data saving error!", 
			 		 content = @Content(schema = @Schema(implementation = UserMaster.class))),
	    @ApiResponse(responseCode = "500", 
			 		 description = "Internal server error!", 
			 		 content = @Content(schema = @Schema(implementation = UserMaster.class)))
	})
	@PutMapping(URLConstants.ContactURL.UPDATE_CONTACT_STATUS)
	public ResponseEntity<Response> updateRequestStatus(@PathVariable Long contactId, @PathVariable Integer status) throws ChatExceptionService {
		Response response = null;
		String message = null;
		
		message = contactService.updateRequestStatus(contactId, status);
		
		if (message != null) {
			response = new Response(ResponseStatus.OK, ResponseMessages.UPDATED_MESSAGE, message);
		} else {
			response = new Response(ResponseStatus.INTERNAL_SERVER_ERROR, ResponseMessages.UNABLE_UPDATED_MESSAGE, message);
		}
		
		return getOKResponseEntity(response);
	}
	
//	========================================================
	
}
