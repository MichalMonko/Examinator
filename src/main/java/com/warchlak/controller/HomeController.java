package com.warchlak.controller;


import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
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
		List<Major> majors = questionService.getMajors();
		for (Major major : majors)
		{
			for (Course course : major.getCourses())
			{
				System.out.println(course.getName());
			}
		}
		
		model.addAttribute("majors", majors);
		return "courseList";
	}
	
	@RequestMapping(value = "/showAddQuestionForm")
	public ModelAndView showAddQuestionForm(ServletRequest request, ModelMap model, @ModelAttribute("courseId") int courseId,
	                                        @ModelAttribute("question") Question question)
	{
		Course course = questionService.getCourse(courseId);
		
		model.addAttribute("course", course);
		model.addAttribute("courseId", courseId);
		
		return new ModelAndView("addQuestionForm", model);
	}
	
	@PostMapping(value = "/addQuestion")
	public ModelAndView addQuestion(ModelMap model, @ModelAttribute("question") Question question,
	                                ServletRequest request)
	{
		boolean success = true;
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		Course course = questionService.getCourse(courseId);
		
		if (question.getContent() == null)
		{
			success = false;
		}
		else if (request.getParameterValues("answer") != null)
		{
			String[] answersContent = request.getParameterValues("answer");
			List<Answer> answers = new ArrayList<>();
			
			for (String answerString : answersContent)
			{
				Answer answer = new Answer();
				answer.setContent(answerString);
				answers.add(answer);
			}
			question.setAnswers(answers);
		}
		if (course != null)
		{
			
			question.setCourse(course);
			questionService.saveQuestion(question);
		}
		else
		{
			success = false;
		}
		
		
		model.addAttribute("courseId", courseId);
		model.addAttribute("success", success);
		
		return new ModelAndView("addQuestionForm", model);
	}
}
