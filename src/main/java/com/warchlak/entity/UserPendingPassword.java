package com.warchlak.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Table(name = "pending_passwords")
public class UserPendingPassword
{
	private static final int expirationTimeInMinutes = 1440;
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@NotNull
	@Column(name = "password")
	private String pendingPassword;
	
	@NotNull
	@OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH},
	fetch = FetchType.EAGER)
	@JoinColumn(name = "username")
	private User user;
	
	@NotNull
	@Column(name = "expiration_date")
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	public UserPendingPassword()
	{
		this.expirationDate = calculateExpirationDate();
	}
	
	public UserPendingPassword(User user, String pendingPassword)
	{
		this.pendingPassword = pendingPassword;
		this.user = user;
		this.expirationDate = calculateExpirationDate();
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getPendingPassword()
	{
		return pendingPassword;
	}
	
	public void setPendingPassword(String pendingPassword)
	{
		this.pendingPassword = pendingPassword;
	}
	
	public User getUser()
	{
		return user;
	}
	
	public void setUser(User user)
	{
		this.user = user;
	}
	
	public Date getExpirationDate()
	{
		return expirationDate;
	}
	
	public void setExpirationDate(Date expirationDate)
	{
		this.expirationDate = expirationDate;
	}
	
	private Date calculateExpirationDate()
	{
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
		
		return new Date(calendar.getTime().getTime());
	}
}
