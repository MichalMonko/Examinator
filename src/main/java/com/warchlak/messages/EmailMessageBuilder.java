package com.warchlak.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public abstract class EmailMessageBuilder
{
	protected final CustomMessageSource messageSource;
	protected String applicationUrl;
	protected String controllerPath;
	protected String token;
	protected String recipientEmail;
	protected String content;
	protected String subject;
	
	@Autowired
	public EmailMessageBuilder(CustomMessageSource messageSource)
	{
		this.messageSource = messageSource;
	}
	
	public void setApplicationUrl(String applicationUrl)
	{
		this.applicationUrl = applicationUrl;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
	
	public void setRecipientEmail(String recipientEmail)
	{
		this.recipientEmail = recipientEmail;
	}
	
	public abstract SimpleMailMessage buildEmail();
	
}
