package com.warchlak.exceptionHandling.Exception;

public class UnathorizedAccessException extends RuntimeException
{
	public UnathorizedAccessException(String message)
	{
		super(message);
	}
}
