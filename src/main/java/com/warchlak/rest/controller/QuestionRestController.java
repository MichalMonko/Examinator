package com.warchlak.rest.controller;

import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import com.warchlak.rest.exceptionHandling.ResourceNotFoundException;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionRestController
{
	private final QuestionServiceInterface service;
	
	@Autowired
	public QuestionRestController(QuestionServiceInterface questionService)
	{
		this.service = questionService;
	}
	
	@GetMapping("/majors")
	public List<Major> getMajors()
	{
		return service.getMajors();
	}
	
	@GetMapping("/majors/{majorId}")
	public Major getMajor(@PathVariable int majorId)
	{
		Major major = service.getMajor(majorId);
		if (major == null)
		{
			throw new ResourceNotFoundException("Major with id " + majorId + " doesn't exist");
		}
		return major;
	}
	
	@GetMapping("/courses")
	public List<Course> getCourses()
	{
		return service.getCourses();
	}
	
	@GetMapping("/courses/{courseId}")
	public Course getCourse(@PathVariable int courseId)
	{
		Course course = service.getCourse(courseId);
		if (course == null)
		{
			throw new ResourceNotFoundException("Course with id " + courseId + " doesn't exists");
		}
		return course;
	}
	
	@PostMapping(value = "/questions/{courseId}", consumes = "application/json")
	public void addQuestions(@PathVariable("courseId") int courseId, @RequestBody List<Question> questions)
	{
	}
}
