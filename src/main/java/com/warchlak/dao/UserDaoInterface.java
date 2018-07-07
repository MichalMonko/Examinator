package com.warchlak.dao;

import com.warchlak.entity.User;

public interface UserDaoInterface
{
	public int getUser(int userId);
	public int saveUser(User user);
	public int removeUser(int UserId);
	public User getUserByEmail(String email);
	public User getUserByUsername(String username);
}
