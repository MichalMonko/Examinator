package com.warchlak.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class CustomMessageSource
{
	
	final private MessageSource messageSource;
	
	final private MyFixedLocaleResolver localeResolver = new MyFixedLocaleResolver();
	
	@Autowired
	public CustomMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	public String getMessage(String messageCode, Object[] params)
	{
		return messageSource.getMessage(messageCode, params, localeResolver.getDefaultLocale() );
	}
	
	public String getMessage(String messageCode)
	{
		return messageSource.getMessage(messageCode, null, localeResolver.getDefaultLocale());
	}
	
	private class MyFixedLocaleResolver
	{
		private Locale locale = new Locale("pl", "PL");
		
		private Locale getDefaultLocale()
		{
			return locale;
		}
	}
	
}
