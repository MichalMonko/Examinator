package com.warchlak.messages;

import com.warchlak.entity.User;
import com.warchlak.events.UserDrivenEvent;
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
	
	public SimpleMailMessage buildEmail()
	{
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		String messageText = content + controllerPath + token;
		
		mailMessage.setSubject(subject);
		mailMessage.setText(messageText);
		mailMessage.setTo(recipientEmail);
		
		return mailMessage;
	}
	
	public void populateBuilderFields(UserDrivenEvent event)
	{
		User user = event.getUser();
		
		String recipientEmail = user.getEmail();
		String applicationUrl = event.getApplicationUrl();
		String token = event.getToken();
		
		this.applicationUrl = applicationUrl;
		this.recipientEmail = recipientEmail;
		this.token = token;
	}
	
}
