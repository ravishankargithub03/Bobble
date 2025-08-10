package com.babble.chat.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.support.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.babble.chat.response.Response;

@ControllerAdvice
public class ChatExceptionService extends Exception {

	private static final long serialVersionUID = -2399611517082731182L;

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.toList());
        
        Response response = new Response(400, "Validation failed", errorMessages);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
