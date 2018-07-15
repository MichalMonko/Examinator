package com.warchlak.events;

import com.warchlak.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserPasswordChangeEvent extends ApplicationEvent implements UserDrivenEvent
{
	private User user;
	private String applicationUrl;
	private String token;
	
	public UserPasswordChangeEvent(Object source,String token, String applicationUrl)
	{
		super(source);
		this.user = (User) source;
		this.token = token;
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
	
	@Override
	public String getToken()
	{
		return token;
	}
	
	public void setToken(String token)
	{
		this.token = token;
	}
}
