package com.warchlak.dao;

import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;

import java.util.List;

public interface QuestionDaoInterface
{
	
	public List<Major> getMajors();
	
	public Major getMajor(int id);
	
	public List<Course> getCourses();
	
	public Course getCourse(int id);
	
	public Question getQuestion(int id);
	
	public int saveQuestion(Question question);
	
	public int saveAnswer(Answer answer);
	
	public int saveCourse(Course course);
	
	Course saveQuestions(int courseId, List<Question> questions);
	
	Course getCourseWithQuestions(int courseId);
}
