package com.warchlak.service;

import com.warchlak.dao.QuestionDao;
import com.warchlak.dao.QuestionDaoInterface;
import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class QuestionService implements QuestionServiceInterface
{
	
	private final QuestionDaoInterface questionDAO;
	
	@Autowired
	public QuestionService(QuestionDaoInterface questionDAO)
	{
		this.questionDAO = questionDAO;
	}
	
	@Override
	@Transactional
	public List<Major> getMajors()
	{
		return questionDAO.getMajors();
	}
	
	@Override
	@Transactional
	public Major getMajor(int id)
	{
		return questionDAO.getMajor(id);
	}
	
	@Override
	@Transactional
	public Question getQuestion(int id)
	{
		return questionDAO.getQuestion(id);
	}
	
	@Override
	@Transactional
	public List<Course> getCourses()
	{
		return questionDAO.getCourses();
	}
	
	@Override
	@Transactional
	public Course getCourse(int id)
	{
		return questionDAO.getCourse(id);
	}
	
	@Override
	@Transactional
	public int saveQuestion(Question question)
	{
		return questionDAO.saveQuestion(question);
	}
	
	@Override
	@Transactional
	public int saveAnswer(Answer answer)
	{
		return questionDAO.saveAnswer(answer);
	}
	
	@Override
	@Transactional
	public int saveCourse(Course course)
	{
		return questionDAO.saveCourse(course);
	}
	
	@Override
	@Transactional
	public Course saveQuestions(int courseId, List<Question> questions)
	{
		return questionDAO.saveQuestions(courseId, questions);
	}
}
