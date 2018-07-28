package com.warchlak.exceptionHandling.Exception;

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
