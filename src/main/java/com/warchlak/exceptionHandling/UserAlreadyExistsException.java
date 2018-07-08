package com.warchlak.exceptionHandling;

public class UserAlreadyExistsException extends RuntimeException
{
	public UserAlreadyExistsException(String message)
	{
		super(message);
	}
	
}
