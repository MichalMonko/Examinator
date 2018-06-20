package com.warchlak.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "content")
	private String content;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "course_id")
	private Course course;
	
	@OneToMany
	@JoinTable(name = "questions_answers_relationship",
			joinColumns = @JoinColumn(name = "question_id"),
			inverseJoinColumns = @JoinColumn(name = "answer_id"))
	private List<Answer> answers;
	
	public Question(String content, Course course)
	{
		this.content = content;
		this.course = course;
	}
	
	public Question(String content, Course course, List<Answer> answers)
	{
		this.content = content;
		this.course = course;
		this.answers = answers;
	}
	
	public Question()
	{
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getContent()
	{
		return content;
	}
	
	public void setContent(String content)
	{
		this.content = content;
	}
	
	public Course getCourse()
	{
		return course;
	}
	
	public void setCourse(Course course)
	{
		this.course = course;
	}
	
	public List<Answer> getAnswers()
	{
		return answers;
	}
	
	public void setAnswers(List<Answer> answers)
	{
		this.answers = answers;
	}
}
