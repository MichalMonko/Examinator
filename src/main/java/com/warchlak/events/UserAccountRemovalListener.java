package com.warchlak.events;

import com.warchlak.messages.UserAccountRemoveMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserAccountRemovalListener implements ApplicationListener<UserAccountRemovalEvent>
{
	private final JavaMailSender mailSender;
	private final UserAccountRemoveMessageBuilder messageBuilder;
	
	@Autowired
	public UserAccountRemovalListener(JavaMailSender mailSender, UserAccountRemoveMessageBuilder messageBuilder)
	{
		this.mailSender = mailSender;
		this.messageBuilder = messageBuilder;
	}
	
	
	@Override
	public void onApplicationEvent(UserAccountRemovalEvent userAccountRemovalEvent)
	{
		messageBuilder.populateBuilderFields(userAccountRemovalEvent);
		mailSender.send(messageBuilder.buildEmail());
	}
}
