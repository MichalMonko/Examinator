package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.User;

public interface UserServiceInterface
{
	int saveUser(UserDTO userDTO);
	
	User getUser(int UserId);
	
	User getUserByEmail(String email);
	
	User getUserByUsername(String username);
}
