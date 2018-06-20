package com.warchlak.controller;

import com.warchlak.entity.Major;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public List<Major> getMajors() {
		return service.getMajors();
	}
}
