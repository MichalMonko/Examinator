package com.warchlak.service;

import com.warchlak.dao.QuestionDao;
import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements QuestionServiceInterface
{
	
	private final QuestionDao questionDAO;
	
	@Autowired
	public QuestionService(QuestionDao questionDAO)
	{
		this.questionDAO = questionDAO;
	}
	
	@Override
	public List<Major> getMajors()
	{
		return null;
	}
	
	@Override
	public Major getMajor(int id)
	{
		return null;
	}
	
	@Override
	public List<Course> getCoursesForMajor(int majorId)
	{
		return null;
	}
	
	@Override
	public Course getCoures(int id)
	{
		return null;
	}
	
	@Override
	public List<Question> getQuestionForCourse(int courseId)
	{
		return null;
	}
	
	@Override
	public Question getQuestion(int id)
	{
		return null;
	}
	
	@Override
	public List<Answer> getAnswerForQuestion(int questionId)
	{
		return null;
	}
	
	@Override
	public void saveQuestion(Question question)
	{
	
	}
	
	@Override
	public void saveAnswer(Answer answer)
	{
	
	}
}
