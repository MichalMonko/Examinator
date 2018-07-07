package com.warchlak.DTO;

import com.sun.istack.internal.NotNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserDTO
{
	@NotNull
	@Size(min = 2,max = 30)
	@NotBlank
	private String username;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = "[1-9]{6}@student\\.pwr\\.edu\\.pl")
	private String email;
	
	@NotBlank
	@NotNull
	@Size(min = 2,max = 30)
	private String password;
	
	@NotBlank
	@NotNull
	@Size(min = 2,max = 30)
	private String confirmedPassword;
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getConfirmedPassword()
	{
		return confirmedPassword;
	}
	
	public void setConfirmedPassword(String confirmedPassword)
	{
		this.confirmedPassword = confirmedPassword;
	}
}
