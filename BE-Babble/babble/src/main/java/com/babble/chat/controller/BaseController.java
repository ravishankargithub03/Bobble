package com.babble.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.babble.chat.response.Response;

public class BaseController {
	
	public ResponseEntity<Response> getOKResponseEntity(Response response) {
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
