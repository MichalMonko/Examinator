package com.warchlak.events;

import com.warchlak.entity.User;

public interface UserDrivenEvent
{
	public User getUser();
	
	public String getToken();
	
	public String getApplicationUrl();
}
