package com.warchlak.dao;

import com.warchlak.entity.User;

public interface UserDaoInterface
{
	public void saveUser(User user);
	public User getUserByEmail(String email);
	public User getUserByUsername(String username);
}
