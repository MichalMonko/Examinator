package com.warchlak.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@Entity
@Table(name = "validation_tokens")
public class ValidationToken
{
	public enum TOKEN_TYPE {
		REGISTER, CHANGE_PASSWORD, REMOVE_ACCOUNT
	};
	
	public static final int expirationTimeInMinutes = 1440;
	
	public ValidationToken()
	{
	}
	
	public ValidationToken(String token, User user)
	{
		this.token = token;
		this.user = user;
		this.expirationDate = calculateExpirationDate();
		this.tokenType = TOKEN_TYPE.REGISTER;
	}
	
	public ValidationToken(String token, User user, TOKEN_TYPE tokenType)
	{
		this.token = token;
		this.user = user;
		this.expirationDate = calculateExpirationDate();
		this.tokenType = tokenType;
	}
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "token")
	private String token;
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "username")
	private User user;
	
	@Column(name = "expiration_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date expirationDate;
	
	@Column(name = "type")
	@Enumerated(EnumType.STRING)
	private TOKEN_TYPE tokenType;
	
	public Date calculateExpirationDate()
	{
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		calendar.setTime(new Timestamp(calendar.getTime().getTime()));
		calendar.add(Calendar.MINUTE, expirationTimeInMinutes);
		
		return new Date(calendar.getTime().getTime());
	}
	
	public static int getExpirationTimeInMinutes()
	{
		return expirationTimeInMinutes;
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getToken()
	{
		return token;
	}
	
	public void setToken(String token)
	{
		this.token = token;
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
	
	public TOKEN_TYPE getTokenType()
	{
		return tokenType;
	}
	
	public void setTokenType(TOKEN_TYPE token_type)
	{
		this.tokenType = token_type;
	}
}
