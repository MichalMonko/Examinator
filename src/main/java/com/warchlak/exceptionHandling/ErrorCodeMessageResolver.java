package com.warchlak.exceptionHandling;

import com.warchlak.messages.CustomMessageSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ErrorCodeMessageResolver
{
	private CustomMessageSource messageSource;
	
	@Autowired
	public ErrorCodeMessageResolver(CustomMessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	public String resolveErrorMessage(final int errorCode)
	{
		String errorMessage;
		switch (errorCode)
		{
			case 404:
				errorMessage = messageSource.getMessage("error.404");
				break;
			
			case 400:
				errorMessage = messageSource.getMessage("error.400");
				break;
			
			case 401:
				errorMessage = messageSource.getMessage("error.401");
				break;
			
			case 403:
				errorMessage = messageSource.getMessage("error.403");
				break;
			
			case 500:
				errorMessage = messageSource.getMessage("error.500");
				break;
			
			default:
				errorMessage = messageSource.getMessage("error.other");
				break;
		}
		
		return errorMessage;
	}
	
	public String getErrorCause(final int errorCode)
	{
		return HttpStatus.valueOf(errorCode).getReasonPhrase();
	}
}
