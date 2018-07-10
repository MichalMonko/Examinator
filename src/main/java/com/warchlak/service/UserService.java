package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.dao.UserDaoInterface;
import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;
import com.warchlak.exceptionHandling.UserAlreadyExistsException;
import com.warchlak.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Transactional("userTransactionManager")
public class UserService implements UserServiceInterface
{
	@Resource(name = "userDAO")
	private final UserDaoInterface userDao;
	
	@Autowired
	public UserService(UserDaoInterface userDao)
	{
		this.userDao = userDao;
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
	public ValidationToken createValidationToken(User user, String token)
	{
		ValidationToken validationToken = new ValidationToken(token, user);
		userDao.saveToken(validationToken);
		
		return validationToken;
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
}
