package com.warchlak.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Question;
import com.warchlak.messages.CustomMessageSource;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController
{
	private QuestionServiceInterface questionService;
	private final CustomMessageSource messageSource;
	
	@Autowired
	public QuestionController(QuestionServiceInterface questionService, CustomMessageSource messageSource)
	{
		this.questionService = questionService;
		this.messageSource = messageSource;
	}
	
	@RequestMapping(value = "/showAddForm")
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
	
	@RequestMapping("/showAddFiles")
	public ModelAndView showAddQuestionFiles(ModelMap model, @ModelAttribute("courseId") int courseId)
	{
		Course course = questionService.getCourse(courseId);
		
		model.addAttribute("course", course);
		model.addAttribute("courseId", courseId);
		
		return new ModelAndView("ReadQuestionsFromFiles", model);
	}
	
	@PostMapping(value = "/add")
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
	
	@PostMapping("/addFromFiles")
	public ModelAndView addFromFiles(ModelMap model, @ModelAttribute("questionsAsJson") String jsonString,
	                                 @ModelAttribute("courseId") int courseId, HttpServletRequest request)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		try
		{
			List<Question> questions = objectMapper.readValue(jsonString, new TypeReference<List<Question>>()
			{
			});
			
			questionService.saveQuestions(courseId, questions);
			
			model.addAttribute("successMessage", messageSource.getMessage("success.questionsAdded"));
			
		} catch (IOException e)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidJsonData"));
		} catch (Exception e)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.other"));
		}
		
		return new ModelAndView("ReadQuestionsFromFiles", model);
	}
}
