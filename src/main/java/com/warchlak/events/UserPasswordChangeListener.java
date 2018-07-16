package com.warchlak.events;

import com.warchlak.messages.UserPasswordChangeMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class UserPasswordChangeListener implements ApplicationListener<UserPasswordChangeEvent>
{
	final private
	JavaMailSender emailSender;
	
	final private
	UserPasswordChangeMessageBuilder messageBuilder;
	
	@Autowired
	public UserPasswordChangeListener(JavaMailSender emailSender, UserPasswordChangeMessageBuilder messageBuilder)
	{
		this.emailSender = emailSender;
		this.messageBuilder = messageBuilder;
	}
	
	@Override
	public void onApplicationEvent(UserPasswordChangeEvent userPasswordChangeEvent)
	{
		messageBuilder.populateBuilderFields(userPasswordChangeEvent);
		emailSender.send(messageBuilder.buildEmail());
	}
}
