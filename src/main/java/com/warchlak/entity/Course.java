package com.warchlak.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
			CascadeType.PERSIST, CascadeType.REFRESH})
	@JoinColumn(name = "major_id")
	private Major major;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
	private List<Question> questions;
	
	public Course(String name, Major major)
	{
		this.name = name;
		this.major = major;
	}
	
	public Course(String name, Major major, List<Question> questions)
	{
		this.name = name;
		this.major = major;
		this.questions = questions;
	}
	
	public Course()
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
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Major getMajor()
	{
		return major;
	}
	
	public void setMajor(Major major)
	{
		this.major = major;
	}
	
	public List<Question> getQuestions()
	{
		return questions;
	}
	
	public void setQuestions(List<Question> questions)
	{
		this.questions = questions;
	}
}
