package com.warchlak.exceptionHandling;

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
