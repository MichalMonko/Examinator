package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.User;

public interface UserServiceInterface
{
	void saveUser(UserDTO userDTO);
	
	User getUserByEmail(String email);
	
	User getUserByUsername(String username);
}
