package com.warchlak.service;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;

public interface UserServiceInterface
{
	User saveUser(UserDTO userDTO);
	
	void updateUser(User user);
	
	User getUserByEmail(String email);
	
	User getUserByUsername(String username);
	
	ValidationToken createValidationToken(User user, String token);
	
	ValidationToken getValidationToken(String token);
}
