package com.warchlak.dao;

import com.warchlak.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

@Repository(value = "userDAO")
public class UserDao implements UserDaoInterface
{
	@Resource(name = "userSessionFactory")
	private SessionFactory sessionFactory;
	
	@Override
	public int getUser(int userId)
	{
		User user;
		Session session = sessionFactory.getCurrentSession();
		user = session.get(User.class, userId);
		
		if (user != null)
		{
			return user.getId();
		}
		else
		{
			return -1;
		}
	}
	
	@Override
	public int saveUser(User user)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(user);
		
		return getUser(user.getId());
	}
	
	@Override
	public int removeUser(int userId)
	{
		return -1;
	}
	
	@Override
	public User getUserByEmail(String email)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<User> query =
				session.createQuery("FROM User WHERE User.email=:email", User.class)
				       .setParameter("email", email);
		
		return query.getSingleResult();
	}
	
	@Override
	public User getUserByUsername(String username)
	{
		Session session = sessionFactory.getCurrentSession();
		Query<User> query =
				session.createQuery("FROM User WHERE User.username=:username", User.class)
				       .setParameter("username", username);
		
		return query.getSingleResult();
	}
}
