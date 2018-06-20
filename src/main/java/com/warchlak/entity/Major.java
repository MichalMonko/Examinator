package com.warchlak.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="majors")
public class Major
{
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "major")
	private List<Course> courses;
	
	public Major(String name)
	{
		this.name = name;
	}
	
	public Major(String name, List<Course> courses)
	{
		this.name = name;
		this.courses = courses;
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
	
	public List<Course> getCourses()
	{
		return courses;
	}
	
	public void setCourses(List<Course> courses)
	{
		this.courses = courses;
	}
}
