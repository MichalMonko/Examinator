package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.dao.UserDaoInterface;
import com.warchlak.entity.User;
import com.warchlak.factory.UserFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements UserServiceInterface
{
	@Resource(name = "userDAO")
	private UserDaoInterface userDao;
	
	@Override
	public int saveUser(UserDTO userDTO)
	{
		
		if (!checkIfUserExists(userDTO))
		{
			User newUser = UserFactory.createUser(userDTO);
			
			return userDao.saveUser(newUser);
		}
		else
		{
			return -1;
		}
		
		
	}
	
	@Override
	public User getUser(int UserId)
	{
		return null;
	}
	
	@Override
	public User getUserByEmail(String email)
	{
		return null;
	}
	
	@Override
	public User getUserByUsername(String username)
	{
		return null;
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
