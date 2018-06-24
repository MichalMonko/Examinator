package com.warchlak.controller;


import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.service.QuestionService;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class HomeController
{
	@Autowired
	private QuestionServiceInterface questionService;
	
	@RequestMapping(value = "/")
	public String getHomePage()
	{
		return "home";
	}
	
	@RequestMapping(value = "/showCoursesList")
	public String getCoursesList(Model model)
	{
		List<Major> majors =  questionService.getMajors();
		for(Major major : majors) {
			for(Course course : major.getCourses()) {
				System.out.println(course.getName());
			}
		}
		
		model.addAttribute("majors", majors);
		return "courseList";
	}
	
	@RequestMapping(value = "/addQuestion")
	public String addQuestion(Model model, @ModelAttribute("courseId") int courseId) {
		return "placeholder";
	}
}
