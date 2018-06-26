package com.warchlak.entity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "is_correct")
	private boolean correct;
	
	public Answer(String content, boolean isCorrect)
	{
		this.content = content;
		this.correct = isCorrect;
	}
	
	public Answer()
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
	
	public boolean isCorrect()
	{
		return correct;
	}
	
	public void setCorrect(boolean correct)
	{
		this.correct = correct;
	}
}
