package com.warchlak.dao;

import com.warchlak.entity.User;
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
}
