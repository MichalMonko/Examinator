package com.warchlak.entity;

import javax.persistence.*;

@Entity
@Table(name = "answers")
public class Answer
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JoinColumn(name = "id")
	private int id;
	
	@JoinColumn(name = "content")
	private String content;
	
	@JoinColumn(name = "is_correct")
	private boolean isCorrect;
	
	public Answer(String content, boolean isCorrect)
	{
		this.content = content;
		this.isCorrect = isCorrect;
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
		return isCorrect;
	}
	
	public void setCorrect(boolean correct)
	{
		isCorrect = correct;
	}
}
