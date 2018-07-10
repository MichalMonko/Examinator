package com.warchlak.events;

import com.warchlak.entity.User;
import com.warchlak.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<UserRegistrationEvent>
{
	@Autowired
	private UserServiceInterface userService;
	
	@Autowired
	JavaMailSender emailSender;
	
	@Override
	public void onApplicationEvent(UserRegistrationEvent userRegistrationEvent)
	{
		User user = userRegistrationEvent.getUser();
		String token = UUID.randomUUID().toString();
		
		userService.createValidationToken(user, token);
		
		String recipientEmail = user.getEmail();
		String registrationUrl = userRegistrationEvent.getAppUrl() +
				"/authentication/confirmRegistration?token=" + token;
		String subject = "Potwierdzenie rejestracji w serwisie TESTOWNIKI";
		String content = "Aby aktywować swoje konto skopiuj poniższy link w okno przeglądarki: " +
				 "\n" + "localhost:8000" + registrationUrl;
		
		SimpleMailMessage message = new SimpleMailMessage();
		
		message.setTo(recipientEmail);
		message.setSubject(subject);
		message.setText(content);
		
		emailSender.send(message);
	}
}
