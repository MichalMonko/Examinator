package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.dao.UserDaoInterface;
import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;
import com.warchlak.events.UserRegistrationEvent;
import com.warchlak.exceptionHandling.ResourceNotFoundException;
import com.warchlak.exceptionHandling.UserAlreadyExistsException;
import com.warchlak.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional("userTransactionManager")
public class UserService implements UserServiceInterface
{
	final private
	JavaMailSender mailSender;
	
	final private
	ApplicationEventPublisher eventPublisher;
	
	@Resource(name = "userDAO")
	private final UserDaoInterface userDao;
	
	@Autowired
	public UserService(UserDaoInterface userDao, JavaMailSender mailSender, ApplicationEventPublisher eventPublisher)
	{
		this.userDao = userDao;
		this.mailSender = mailSender;
		this.eventPublisher = eventPublisher;
	}
	
	@Override
	public User saveUser(UserDTO userDTO)
	{
		if (!checkIfUserExists(userDTO))
		{
			User newUser = UserFactory.createUser(userDTO);
			userDao.saveUser(newUser);
			return newUser;
		}
		else
		{
			throw new UserAlreadyExistsException("Użytkownik a danym loginie lub adresie email już istnieje, wprowadź inne dane.");
		}
	}
	
	@Override
	public User getUserByEmail(String email)
	{
		return userDao.getUserByEmail(email);
	}
	
	@Override
	public User getUserByUsername(String username)
	{
		return userDao.getUserByUsername(username);
	}
	
	private boolean checkIfUserExists(UserDTO userDTO)
	{
		String username = userDTO.getUsername();
		String email = userDTO.getEmail();
		
		User foundUser = getUserByUsername(username);
		if (foundUser != null)
		{
			return true;
		}
		else
		{
			return (getUserByEmail(email) != null);
		}
	}
	
	@Override
	public void createValidationToken(User user, String token)
	{
		ValidationToken validationToken;
		if (userDao.getValidationToken(token) == null)
		{
			validationToken = new ValidationToken(token, user);
			userDao.saveToken(validationToken);
		}
	}
	
	@Override
	public ValidationToken getValidationToken(String token)
	{
		return userDao.getValidationToken(token);
	}
	
	@Override
	public void updateUser(User user)
	{
		userDao.saveUser(user);
	}
	
	@Override
	public void updateUserToken(String userEmail, String token, String applicationUrl)
	{
		User user;
		if ((user = getUserByEmail(userEmail)) != null)
		{
			userDao.updateUserToken(user.getUsername(), token);
			
			String recipientEmail = user.getEmail();
			String registrationUrl = applicationUrl +
					"/authentication/confirmRegistration?token=" + token;
			String subject = "Potwierdzenie rejestracji w serwisie TESTOWNIKI";
			String content = "Aby aktywować swoje konto skopiuj poniższy link w okno przeglądarki: " +
					"\n" + "localhost:8000" + registrationUrl;
			
			SimpleMailMessage message = new SimpleMailMessage();
			
			message.setTo(recipientEmail);
			message.setSubject(subject);
			message.setText(content);
			
			mailSender.send(message);
		}
		else
		{
			throw new ResourceNotFoundException("User with given email cannot be found");
		}
	}
	
	@Override
	public void registerUser(UserDTO userDTO, String applicationUrl)
	{
		User user = saveUser(userDTO);
		eventPublisher.publishEvent(new UserRegistrationEvent(user, applicationUrl));
	}
}
