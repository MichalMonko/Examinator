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
	
	public Course getCourse(int id);
	
	public Question getQuestion(int id);
	
	public void saveQuestion(Question question);
	
	public void saveAnswer(Answer answer);
	
}
