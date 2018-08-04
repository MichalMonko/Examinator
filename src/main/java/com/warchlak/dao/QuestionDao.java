package com.warchlak.dao;

import com.warchlak.entity.*;
import com.warchlak.exceptionHandling.Exception.ResourceNotFoundException;
import org.hibernate.Hibernate;
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
		return query.getResultList();
	}
	
	@Override
	public Major getMajor(int id)
	{
		Session session = sessionFactory.getCurrentSession();
		return session.get(Major.class, id);
	}
	
	@Override
	public List<Course> getCourses()
	{
		Session session = sessionFactory.getCurrentSession();
		Query<Course> query = session.createQuery("From Course", Course.class);
		return query.getResultList();
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
	public int saveQuestion(Question question)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(question);
		return question.getId();
	}
	
	@Override
	public int saveAnswer(Answer answer)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(answer);
		return answer.getId();
	}
	
	@Override
	public int saveCourse(Course course)
	{
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(course);
		return course.getId();
	}
	
	@Override
	public Course saveQuestions(int courseId, List<Question> questions)
	{
		Session session = sessionFactory.getCurrentSession();
		Course course = session.get(Course.class, courseId);
		if (course == null)
		{
			throw new ResourceNotFoundException("Course with id " + courseId + " cannot be found");
		}
		
		for (Question question : questions)
		{
			question.setCourse(course);
		}
		
		Hibernate.initialize(course.getQuestions());
		course.getQuestions().addAll(questions);
		
		session.saveOrUpdate(course);
		return course;
	}
	
	@Override
	public Course getCourseWithQuestions(int courseId)
	{
		Session session = sessionFactory.getCurrentSession();
		Course course = session.get(Course.class, courseId);
		
		if(course == null) {
			throw new ResourceNotFoundException("Course with id: " + courseId + " doesn't exist");
		}
		Hibernate.initialize(course.getQuestions());
		
		return course;
	}
}
