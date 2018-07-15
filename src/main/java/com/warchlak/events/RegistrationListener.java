package com.warchlak.events;

import com.warchlak.entity.User;
import com.warchlak.messages.UserRegistrationEmailMessageBuilder;
import com.warchlak.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<UserRegistrationEvent>
{
	private final UserServiceInterface userService;
	
	private final JavaMailSender emailSender;
	
	private final UserRegistrationEmailMessageBuilder messageBuilder;
	
	@Autowired
	public RegistrationListener(UserServiceInterface userService, JavaMailSender emailSender,
	                            UserRegistrationEmailMessageBuilder messageBuilder)
	{
		this.userService = userService;
		this.emailSender = emailSender;
		this.messageBuilder = messageBuilder;
	}
	
	@Override
	public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent)
	{
		User user = userRegistrationEvent.getUser();
		String token = UUID.randomUUID().toString();
		
		userService.createValidationToken(user, token);
		
		String recipientEmail = user.getEmail();
		String applicationUrl = userRegistrationEvent.getAppUrl();
		
		messageBuilder.setApplicationUrl(applicationUrl);
		messageBuilder.setRecipientEmail(recipientEmail);
		messageBuilder.setToken(token);
		
		emailSender.send(messageBuilder.buildEmail());
	}
}
