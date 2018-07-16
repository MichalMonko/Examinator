package com.warchlak.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAccountRemoveMessageBuilder extends EmailMessageBuilder
{
	@Autowired
	public UserAccountRemoveMessageBuilder(CustomMessageSource messageSource)
	{
		super(messageSource);
		this.content = messageSource.getMessage("emailBuilder.removeUser.content");
		this.subject = messageSource.getMessage("emailBuilder.removeUser.subject");
		this.controllerPath = messageSource.getMessage("emailBuilder.removeUser.url");
	}
}
