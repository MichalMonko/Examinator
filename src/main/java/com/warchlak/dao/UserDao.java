package com.warchlak.dao;

import com.warchlak.entity.User;
import com.warchlak.entity.UserPendingPassword;
import com.warchlak.entity.ValidationToken;
import com.warchlak.exceptionHandling.ResourceNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository(value = "userDAO")
public class UserDao implements UserDaoInterface
{
	@Resource(name = "userSessionFactory")
	private final SessionFactory sessionFactory;
	
	@Autowired
	public UserDao(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public void saveUser(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
	}
	
	@Override
	public void removeUser(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		session.remove(user);
	}
	
	@Override
	public void saveToken(ValidationToken validationToken)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(validationToken);
	}
	
	@Override
	public User getUserByEmail(String email)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<User> query =
				session.createQuery("FROM User WHERE email=:email", User.class)
				       .setParameter("email", email);
		
		if (query.getResultList().isEmpty())
		{
			return null;
		}
		else
		{
			return query.getResultList().get(0);
		}
	}
	
	@Override
	public User getUserByUsername(String username)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<User> query =
				session.createQuery("FROM User WHERE username=:username", User.class);
		query.setParameter("username", username);
		
		if (query.getResultList().isEmpty())
		{
			return null;
		}
		else
		{
			return query.getResultList().get(0);
		}
	}
	
	@Override
	public ValidationToken getValidationToken(String token)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<ValidationToken> query = session.createQuery("FROM ValidationToken where token=:token", ValidationToken.class);
		query.setParameter("token", token);
		
		if (query.getResultList().isEmpty())
		{
			return null;
		}
		else
		{
			return query.getResultList().get(0);
		}
		
	}
	
	
	@Override
	public void updateUserToken(String username, String token)
	{
		updateUserToken(username, token, ValidationToken.TOKEN_TYPE.REGISTER);
	}
	
	@Override
	public void updateUserToken(String username, String token, ValidationToken.TOKEN_TYPE tokenType)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<ValidationToken> query = session.createQuery("FROM ValidationToken where user.username=:username",
				ValidationToken.class);
		query.setParameter("username", username);
		
		if (!query.getResultList().isEmpty())
		{
			ValidationToken validationToken = query.getResultList().get(0);
			
			validationToken.setToken(token);
			validationToken.setTokenType(tokenType);
			validationToken.setExpirationDate(validationToken.calculateExpirationDate());
		}
		else
		{
			throw new ResourceNotFoundException("No validation token for given user found");
		}
	}
	
	@Override
	public void savePendingPassword(UserPendingPassword pendingPassword)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(pendingPassword);
	}
	
	@Override
	public UserPendingPassword getUserPendingPassword(String username)
	{
		Session session = sessionFactory.getCurrentSession();
		
		Query<UserPendingPassword> query = session.createQuery("FROM UserPendingPassword " +
				"WHERE user.username=:username", UserPendingPassword.class);
		query.setParameter("username", username);
		
		if (!query.getResultList().isEmpty())
		{
			return query.getResultList().get(0);
		}
		else
		{
			return null;
		}
	}
	
	
}
