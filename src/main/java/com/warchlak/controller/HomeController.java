package com.warchlak.controller;


import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class HomeController
{
	
	private final QuestionServiceInterface questionService;
	
	@Autowired
	public HomeController(QuestionServiceInterface questionService)
	{
		this.questionService = questionService;
	}
	
	@RequestMapping(value = "/")
	public String getHomePage()
	{
		return "home";
	}
	
	@RequestMapping(value = "/showCoursesList")
	public String getCoursesList(Model model, @RequestParam(value = "searchName" ,required = false) String searchName)
	{
		List<Major> majors = questionService.getMajors();
		
		if (searchName != null)
		{
			model.addAttribute("majors", filterCourses(majors, searchName));
		}
		else
		{
			model.addAttribute("majors", majors);
		}
		return "courseList";
	}
	
	@GetMapping("/showQuestions")
	public ModelAndView showQuestionListForCourse(@ModelAttribute("courseId") int courseId,
	                                              ModelMap model)
	{
		Course course = questionService.getCourseWithQuestions(courseId);
		model.addAttribute("course", course);
		
		return new ModelAndView("showQuestions", model);
	}
	
	private List<Major> filterCourses(List<Major> majors, String name)
	{
		for (Major major : majors)
		{
			major.getCourses().removeIf(course -> !course.getName().contains(name));
		}
		
		majors.removeIf(major -> major.getCourses().isEmpty());
		
		return majors;
	}
	
	
}
