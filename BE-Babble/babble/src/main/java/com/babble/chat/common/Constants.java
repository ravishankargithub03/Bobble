package com.babble.chat.common;

public class Constants {

	public static class ResponseStatus {
		public static final int OK = 200;
		public static final int NO_CONTENT = 204; // No Content
		public static final int UNAUTHORIZED_ACCESS = 401;
		public static final int INVALID_INPUT = 422; // Unprocessable Entity
		public static final int VALIDATION_ERROR = 10021;
		public static final int DATA_SAVING_ERROR = 10025; // Data Saving Error
		public static final int INTERNAL_SERVER_ERROR = 500;
	}
	
	public static class RecordStatus {
		public static final int DELETE = 0;
		public static final Integer ACTIVE = 1; 
		public static final int DRAFT = 2;
		public static final int PENDING = 3; 
		public static final int REVERTED = 4;
		public static final int REJECTED = 5; 
		public static final int APPROVIED = 6;
	}
	
	public static class ResponseMessages {
		public static final String FETCHED_MESSAGE = "Fetched!";
		public static final String SAVE_MESSAGE = "Saved!";
		public static final String UNABLE_TO_SAVE_MESSAGE = "Unable to save!";
		public static final String CREATED_MESSAGE = "Created!";
		public static final String UNABLE_TO_CREATE_MESSAGE = "Unable to create!";
		public static final String DELETED_MESSAGE = "Deleted!";
		public static final String UPDATED_MESSAGE = "Updated!";
		public static final String UNABLE_UPDATED_MESSAGE = "Unable to updated!";
		public static final String ACTIVATED_MESSAGE = "Activated!";
		public static final String DEACTIVATED_MESSAGE = "Deactivated!";
		public static final String ARCHIVED_MESSAGE = "Archived!";
		public static final String UNABLE_TO_ARCHIVE_MESSAGE = "Unable To Archive!";
		public static final String SEND_BACK_TO_REQUESTER_MESSAGE = "The request has been sent back to the requester";
		public static final String HOLD_MESSAGE = "Record is now on hold";
		public static final String REJECTED_MESSAGE = "Rejected";
		public static final String APPROVED_MESSAGE = "Approved";
		public static final String DOWNLOADED_MESSAGE = "Downloaded";
		public static final String SUBMIT_MESSAGE = "Submitted";
		public static final String ADD_MESSAGE = "Added";
		public static final String UNABLE_TO_ADD_MESSAGE = "Unable To Add";
		public static final String DATA_RETRIVAL_MESSAGE = "Data Retrieveed";
		public static final String UPLOAD_MESSAGE = "Uploaded";
		public static final String TRANSMIT_MESSAGE = "Transmitted";
		public static final String RE_SEND_MESSAGE = "Re-sended";
		public static final String SENT_MESSAGE = "Sent";
		public static final String UNABLE_SENT_MESSAGE = "Unable to sent";
		public static final String USED_RECORD_MESSAGE = "Already in use";
		public static final String DATA_NOT_AVAILABLE_MESSAGE = "No data found";
		public static final String INPUT_REQUIRED_MESSAGE = "Provide search input";
		public static final String WITHOUT_SAVING_SUBMIT_MESSAGE = "Please save before submitting";
		public static final String INVALID_DATA_MESSAGE = "Invalid data";
		public static final String ALPHANUMERIC_INPUT_MESSAGE = "Input must be alphanumeric";
	}
	
}
