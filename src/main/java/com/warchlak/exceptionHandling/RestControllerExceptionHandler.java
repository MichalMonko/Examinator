package com.warchlak.exceptionHandling;

import com.warchlak.exceptionHandling.Exception.BindingErrorException;
import com.warchlak.exceptionHandling.Exception.ResourceNotFoundException;
import com.warchlak.exceptionHandling.Exception.UnathorizedAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestControllerExceptionHandler
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
	
	@ExceptionHandler
	ResponseEntity<ErrorResponse> handleUnathorizedAccess(UnathorizedAccessException e)
	{
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage(), System.currentTimeMillis());
		return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
	}
}
