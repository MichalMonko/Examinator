package com.warchlak.events;

import com.warchlak.entity.User;
import org.springframework.context.ApplicationEvent;

public class UserAccountRemovalEvent extends ApplicationEvent implements UserDrivenEvent
{
	private User user;
	private String token;
	private String applicationUrl;
	
	public UserAccountRemovalEvent(Object source, String token, String applicationUrl)
	{
		super(source);
		this.user = (User) source;
		this.token = token;
		this.applicationUrl = applicationUrl;
	}
	
	@Override
	public User getUser()
	{
		return user;
	}
	
	@Override
	public String getToken()
	{
		return token;
	}
	
	@Override
	public String getApplicationUrl()
	{
		return applicationUrl;
	}
}
