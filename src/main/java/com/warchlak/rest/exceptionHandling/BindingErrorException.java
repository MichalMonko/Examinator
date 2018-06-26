package com.warchlak.rest.exceptionHandling;

public class BindingErrorException extends RuntimeException
{
	public BindingErrorException()
	{
		super();
	}
	
	public BindingErrorException(String message)
	{
		super(message);
	}
}
