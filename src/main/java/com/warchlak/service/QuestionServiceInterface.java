package com.warchlak.service;

import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;

import java.util.List;

public interface QuestionServiceInterface
{
	public List<Major> getMajors();
	
	public Major getMajor(int id);
	
	public List<Course> getCourses();
	
	public Course getCourse(int id);
	
	public Question getQuestion(int id);
	
	public int saveQuestion(Question question);
	
	public int saveAnswer(Answer answer);
	
	int saveCourse(Course course);
	
	Course saveQuestions(int courseId, List<Question> questions);
}
