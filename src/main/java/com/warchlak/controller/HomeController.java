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
import org.springframework.web.bind.annotation.*;
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
		model.addAttribute("majors", majors);
		return "courseList";
	}
	
	@RequestMapping(value = "/showAddQuestionForm")
	public ModelAndView showAddQuestionForm(ModelMap model, @ModelAttribute("courseId") int courseId,
	                                        @ModelAttribute("question") Question question)
	{
		Course course = questionService.getCourse(courseId);
		
		model.addAttribute("course", course);
		model.addAttribute("courseId", courseId);
		
		return new ModelAndView("addQuestionForm", model);
	}
	
	@RequestMapping("/showAddQuestionFiles")
	public ModelAndView showAddQuestionFiles(ModelMap model, @ModelAttribute("courseId") int courseId)
	{
		Course course = questionService.getCourse(courseId);
		
		model.addAttribute("course", course);
		model.addAttribute("courseId", courseId);
		
		return new ModelAndView("ReadQuestionsFromFiles", model);
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
			String[] isCorrect = request.getParameterValues("correct");
			List<Answer> answers = new ArrayList<>();
			
			int counter = 0;
			boolean correct = false;
			
			for (String answerString : answersContent)
			{
				correct = (isCorrect[counter].equals("1"));
				
				Answer answer = new Answer();
				answer.setContent(answerString);
				answer.setCorrect(correct);
				answers.add(answer);
				
				counter++;
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
		if (course != null)
		{
			model.addAttribute("course", course);
		}
		
		return new ModelAndView("addQuestionForm", model);
	}
	
	@GetMapping("/showQuestions")
	public ModelAndView showQuestionListForCourse(@ModelAttribute("courseId") int courseId,
	                                              ModelMap model)
	{
		Course course = questionService.getCourseWithQuestions(courseId);
		model.addAttribute("course",course);
		
		return new ModelAndView("showQuestions", model);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
