package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.dao.UserDaoInterface;
import com.warchlak.entity.User;
import com.warchlak.factory.UserFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
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
	@Transactional("userTransactionManager")
	public void saveUser(UserDTO userDTO)
	{
		if (!checkIfUserExists(userDTO))
		{
			User newUser = UserFactory.createUser(userDTO);
			userDao.saveUser(newUser);
		}
		else
		{
			throw new RuntimeException("User already exists");
		}
	}
	
	@Transactional("userTransactionManager")
	@Override
	public User getUserByEmail(String email)
	{
		return userDao.getUserByEmail(email);
	}
	
	@Transactional("userTransactionManager")
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
}
