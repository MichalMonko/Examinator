package com.warchlak.controller;


import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import com.warchlak.service.QuestionService;
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
import java.util.stream.Collectors;

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
	
	@RequestMapping(value = "/showAddQuestionForm")
	public ModelAndView showAddQuestionForm(ModelMap model, @ModelAttribute("courseId") int courseId,
	                                        @RequestParam(value = "questionId", defaultValue = "-1") int questionId,
	                                        @ModelAttribute("question") Question question)
	{
		Course course = questionService.getCourse(courseId);
		
		if (questionId != -1)
		{
			question = questionService.getQuestion(questionId);
			model.addAttribute("question", question);
		}
		
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
	                                @RequestParam(value = "answer") String[] answerRequestItem,
	                                @RequestParam(value = "correct") String[] isCorrectItems,
	                                ServletRequest request)
	{
		boolean success = true;
		int courseId = Integer.parseInt(request.getParameter("courseId"));
		Course course = questionService.getCourse(courseId);
		
		if (question.getContent() == null)
		{
			success = false;
		}
		else if (answerRequestItem != null)
		{
			List<Answer> answers = new ArrayList<>();
			
			int counter = 0;
			boolean correct = false;
			
			for (String answerString : answerRequestItem)
			{
				correct = (isCorrectItems[counter].equals("1"));
				
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
