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
	
	public List<Course> getCoursesForMajor(int majorId);
	
	public Course getCoures(int id);
	
	public List<Question> getQuestionForCourse(int courseId);
	
	public Question getQuestion(int id);
	
	public List<Answer> getAnswerForQuestion(int questionId);
	
	public void saveQuestion(Question question);
	
	public void saveAnswer(Answer answer);
	
}
