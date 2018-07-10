package com.warchlak.events;

import com.warchlak.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserRegistrationEvent extends ApplicationEvent
{
	private User user;
	private String appUrl;
	
	public UserRegistrationEvent(Object source, String appUrl)
	{
		super(source);
		this.user = (User) source;
		this.appUrl = appUrl;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String getAppUrl()
	{
		return appUrl;
	}
	
	public void setAppUrl(String appUrl)
	{
		this.appUrl = appUrl;
	}
}
