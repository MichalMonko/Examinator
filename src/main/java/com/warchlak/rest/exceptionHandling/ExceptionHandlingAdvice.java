package com.warchlak.rest.exceptionHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlingAdvice
{
	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleNotFoundException(ResourceNotFoundException e)
	{
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.NOT_FOUND.value(),
				e.getMessage(),
				System.currentTimeMillis()
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleBadBindingException(BindingErrorException e)
	{
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				e.getMessage(),
				System.currentTimeMillis()
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleUnknownError(Exception e)
	{
		ErrorResponse errorResponse = new ErrorResponse(
				HttpStatus.BAD_REQUEST.value(),
				"Unknown exception during request handling: "
						+ e.getMessage(),
				System.currentTimeMillis()
		);
		
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
