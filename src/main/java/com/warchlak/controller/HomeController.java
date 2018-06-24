package com.warchlak.controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Major;
import com.warchlak.entity.Question;
import com.warchlak.service.QuestionService;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
		model.addAttribute("courseId", courseId);
		model.addAttribute("course", course);
		return new ModelAndView("addQuestionForm", model);
	}
	
	@PostMapping(value = "/addQuestion")
	public String addQuestion(@ModelAttribute("question") Question question,
	                          ServletRequest request)
	{
		if (question.getContent() == null)
		{
			request.setAttribute("success", false);
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
			
			int courseId = Integer.parseInt(request.getParameter("courseId"));
			Course course = questionService.getCourse(courseId);
			
			if(course != null) {
				question.setCourse(course);
				question.setAnswers(answers);
				//TODO save question should return int
				questionService.saveQuestion(question);
				request.setAttribute("success", true);
			}
			else {
				request.setAttribute("success", false);
			}
		}
		return "redirect:/showAddQuestionForm";
	}
}
