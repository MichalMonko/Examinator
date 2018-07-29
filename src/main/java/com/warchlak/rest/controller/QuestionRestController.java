package com.warchlak.rest.controller;

import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import com.warchlak.exceptionHandling.Exception.BindingErrorException;
import com.warchlak.exceptionHandling.Exception.ResourceNotFoundException;
import com.warchlak.exceptionHandling.Exception.UnathorizedAccessException;
import com.warchlak.messages.CustomMessageSource;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class QuestionRestController
{
	private final QuestionServiceInterface service;
	
	private final CustomMessageSource messageSource;
	
	@Autowired
	public QuestionRestController(QuestionServiceInterface questionService, CustomMessageSource messageSource)
	{
		this.service = questionService;
		this.messageSource = messageSource;
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
	public ResponseEntity<Course> addQuestions(@PathVariable("courseId") int courseId, @RequestBody List<Question> questions,
	                                           BindingResult bindingResult)
	{
		if (bindingResult.hasErrors())
		{
			throw new BindingErrorException("Cannot bind POST body, check your data format");
		}
		Course course = service.saveQuestions(courseId, questions);
		
		return new ResponseEntity<>(course, HttpStatus.OK);
	}
	
	@RequestMapping("/unauthorized_access")
	private void throwUnauthorizedException()
	{
		throw new UnathorizedAccessException(messageSource.getMessage("error.401"));
	}
}
