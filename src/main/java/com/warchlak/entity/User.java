package com.warchlak.entity;

import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User
{
	
	@Id
	@Column(name = "username")
	private String username;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "enabled")
	private boolean enabled = false;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinTable(name = "authorities", joinColumns = @JoinColumn(name = "username"),
			inverseJoinColumns = @JoinColumn(name = "authority"))
	private List<Role> roles;
	
	@Nullable
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private ValidationToken validationToken;
	
	@Nullable
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private UserPendingPassword pendingPassword;
	
	public String getUsername()
	{
		return username;
	}
	
	public void setUsername(String username)
	{
		this.username = username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public List<Role> getRoles()
	{
		return roles;
	}
	
	public void setRoles(List<Role> roles)
	{
		this.roles = roles;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public ValidationToken getValidationToken()
	{
		return validationToken;
	}
	
	public void setValidationToken(ValidationToken validationToken)
	{
		this.validationToken = validationToken;
	}
	
	public UserPendingPassword getPendingPassword()
	{
		return pendingPassword;
	}
	
	public void setPendingPassword(UserPendingPassword pendingPassword)
	{
		this.pendingPassword = pendingPassword;
	}
}
