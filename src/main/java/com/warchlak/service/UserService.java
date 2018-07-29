package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.dao.UserDaoInterface;
import com.warchlak.entity.User;
import com.warchlak.entity.UserPendingPassword;
import com.warchlak.entity.ValidationToken;
import com.warchlak.events.UserAccountRemovalEvent;
import com.warchlak.events.UserPasswordChangeEvent;
import com.warchlak.events.UserRegistrationEvent;
import com.warchlak.events.UserTokenResendRequestEvent;
import com.warchlak.exceptionHandling.Exception.ResourceNotFoundException;
import com.warchlak.exceptionHandling.Exception.UserAlreadyExistsException;
import com.warchlak.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional("userTransactionManager")
public class UserService implements UserServiceInterface
{
	final private
	ApplicationEventPublisher eventPublisher;
	
	@Resource(name = "userDAO")
	private final UserDaoInterface userDao;
	
	@Autowired
	public UserService(UserDaoInterface userDao, ApplicationEventPublisher eventPublisher)
	{
		this.userDao = userDao;
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
	public void removeUser(User user)
	{
		userDao.removeUser(user);
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
	public void updateUserToken(String userEmail, String token)
	{
		User user;
		if ((user = getUserByEmail(userEmail)) != null)
		{
			userDao.updateUserToken(user.getUsername(), token);
		}
		else
		{
			throw new ResourceNotFoundException("User with given email cannot be found");
		}
	}
	
	@Override
	public void updateUserToken(User user, String newToken, ValidationToken.TOKEN_TYPE tokenType)
	{
		try
		{
			userDao.updateUserToken(user.getUsername(), newToken, tokenType);
		} catch (ResourceNotFoundException e)
		{
			ValidationToken validationToken = new ValidationToken(newToken, user, tokenType);
			userDao.saveToken(validationToken);
		}
	}
	
	@Override
	public void sendPasswordChangeConfirmationLink(User user, String token, String applicationUrl)
	{
		eventPublisher.publishEvent(new UserPasswordChangeEvent(user, token, applicationUrl));
	}
	
	@Override
	public void sendAccountRemovalConfirmationEmail(User user, String newToken, String applicationUrl)
	{
		eventPublisher.publishEvent(new UserAccountRemovalEvent(user, newToken,applicationUrl));
	}
	
	@Override
	public void registerUser(UserDTO userDTO, String applicationUrl)
	{
		User user = saveUser(userDTO);
		eventPublisher.publishEvent(new UserRegistrationEvent(user, applicationUrl));
	}
	
	public void resendUserToken(User user, String token, String applicationUrl)
	{
		eventPublisher.publishEvent(new UserTokenResendRequestEvent(user, token, applicationUrl));
	}
	
	@Override
	public void saveUserPendingPassword(User user, String newPassword)
	{
		String encryptedPassword = "{bcrypt}" + BCrypt.hashpw(newPassword, BCrypt.gensalt());
		UserPendingPassword pendingPassword = userDao.getUserPendingPassword(user.getUsername());
		
		if (pendingPassword == null)
		{
			pendingPassword = new UserPendingPassword(user, encryptedPassword);
		}
			userDao.savePendingPassword(pendingPassword);
	}
	
	@Override
	public UserPendingPassword getUserPendingPassword(User user)
	{
		return userDao.getUserPendingPassword(user.getUsername());
	}
}
