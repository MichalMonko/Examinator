package com.warchlak.dao;

import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;

public interface UserDaoInterface
{
	public void saveUser(User user);
	public User getUserByEmail(String email);
	public User getUserByUsername(String username);
	public void saveToken(ValidationToken token);
	
	ValidationToken getValidationToken(String token);
	void updateUserToken(String username, String token );
}
