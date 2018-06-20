package com.warchlak.dao;

import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class QuestionDao implements QuestionDaoInterface
{
	
	private final SessionFactory sessionFactory;
	
	@Autowired
	public QuestionDao(SessionFactory sessionFactory)
	{
		this.sessionFactory = sessionFactory;
	}
	
	@Override
	public List<Major> getMajors()
	{
		Session session = sessionFactory.getCurrentSession();
		Query<Major> query = session.createQuery("FROM Major", Major.class);
		return  query.getResultList();
	}
	
	@Override
	public Major getMajor(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		return session.get(Major.class, id);
	}
	
	@Override
	public Course getCourse(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		return session.get(Course.class, id);
	}
	
	@Override
	public Question getQuestion(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		return session.get(Question.class, id);
	}
	
	@Override
	public void saveQuestion(Question question)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(question);
	}
	
	@Override
	public void saveAnswer(Answer answer)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(answer);
	}
}
