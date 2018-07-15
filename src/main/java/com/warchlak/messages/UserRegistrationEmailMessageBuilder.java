package com.warchlak.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationEmailMessageBuilder extends EmailMessageBuilder
{
	
	@Autowired
	public UserRegistrationEmailMessageBuilder(CustomMessageSource messageSource)
	{
		super(messageSource);
		this.content = messageSource.getMessage("emailBuilder.register.content");
		this.subject = messageSource.getMessage("emailBuilder.register.subject");
		this.controllerPath = messageSource.getMessage("emailBuilder.register.url");
	}
	
	@Override
	public SimpleMailMessage buildEmail()
	{
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		
		String messageText = content + controllerPath + token;
		
		mailMessage.setSubject(subject);
		mailMessage.setText(messageText);
		mailMessage.setTo(recipientEmail);
		
		return mailMessage;
	}

}
