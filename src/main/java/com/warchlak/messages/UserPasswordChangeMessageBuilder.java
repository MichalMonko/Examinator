package com.warchlak.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordChangeMessageBuilder extends EmailMessageBuilder
{
	@Autowired
	public UserPasswordChangeMessageBuilder(CustomMessageSource messageSource)
	{
		super(messageSource);
		this.content = messageSource.getMessage("emailBuilder.changePassword.content");
		this.subject = messageSource.getMessage("emailBuilder.changePassword.subject");
		this.controllerPath = messageSource.getMessage("emailBuilder.changePassword.url");
	}
}
