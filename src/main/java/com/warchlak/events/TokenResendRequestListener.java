package com.warchlak.events;

import com.warchlak.entity.User;
import com.warchlak.messages.UserRegistrationEmailMessageBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class TokenResendRequestListener implements ApplicationListener<UserTokenResendRequestEvent>
{
	
	private final JavaMailSender emailSender;
	
	private final UserRegistrationEmailMessageBuilder messageBuilder;
	
	@Autowired
	public TokenResendRequestListener(JavaMailSender emailSender,
	                                  UserRegistrationEmailMessageBuilder messageBuilder)
	{
		this.emailSender = emailSender;
		this.messageBuilder = messageBuilder;
	}
	
	@Override
	public void onApplicationEvent(UserTokenResendRequestEvent tokenResendEvent)
	{
		User user = tokenResendEvent.getUser();
		
		String recipientEmail = user.getEmail();
		String applicationUrl = tokenResendEvent.getApplicationUrl();
		String token = tokenResendEvent.getToken();
		
		messageBuilder.setApplicationUrl(applicationUrl);
		messageBuilder.setRecipientEmail(recipientEmail);
		messageBuilder.setToken(token);
		
		emailSender.send(messageBuilder.buildEmail());
	}
}
