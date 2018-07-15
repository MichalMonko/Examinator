package com.warchlak.events;

import com.warchlak.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserTokenResendRequestEvent extends ApplicationEvent
{
	private User user;
	private String applicationUrl;
	private String token;
	
	public UserTokenResendRequestEvent(Object source, String token, String applicationUrl)
	{
		super(source);
		this.token = token;
		this.user = (User) source;
		this.applicationUrl = applicationUrl;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public String getApplicationUrl()
	{
		return applicationUrl;
	}
	
	public void setApplicationUrl(String applicationUrl)
	{
		this.applicationUrl = applicationUrl;
	}
	
	public String getToken()
	{
		return token;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
}
