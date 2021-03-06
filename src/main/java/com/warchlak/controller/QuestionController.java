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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
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
	                                        @RequestParam(value = "questionId", defaultValue = "-1") int questionId
	                                        )
	{
		Course course = questionService.getCourse(courseId);
		
		if (questionId != -1)
		{
			Question question = questionService.getQuestion(questionId);
			model.addAttribute("question", question);
		}
		
		model.addAttribute("course", course);
		model.addAttribute("courseId", courseId);
		
		return new ModelAndView("addQuestionForm", model);
	}
	
	@RequestMapping(value = "/showEditQuestionForm/{questionId}")
	public ModelAndView showEditQuestionForm(ModelMap model, @PathVariable("questionId") int questionId)
	{
		Question question = questionService.getQuestion(questionId);
		Course course = question.getCourse();
		
		model.addAttribute("question", question).addAttribute("course", course)
		     .addAttribute("courseId", course.getId());
		
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
			
			for (String answerString : answerRequestItem)
			{
				boolean correct = (isCorrectItems[counter].equals("1"));
				
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
	                                 @ModelAttribute("courseId") int courseId)
	{
		ObjectMapper objectMapper = new ObjectMapper();
		try
		{
			String decodedJsonString = URLDecoder.decode(jsonString, "UTF-8");
			List<Question> questions = objectMapper.readValue(decodedJsonString, new TypeReference<List<Question>>()
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
	
	@ModelAttribute
	@GetMapping("/quiz/{courseId}")
	public ModelAndView showQuiz(ModelMap model, @PathVariable("courseId") int courseId)
	{
		try
		{
			Course course = questionService.getCourseWithQuestions(courseId);
			
			List<Question> questions = course.getQuestions();
			model.addAttribute(questions);
			
			ObjectMapper objectMapper = new ObjectMapper();
			
			String jsonString;
			jsonString = objectMapper.writeValueAsString(questions);
			jsonString = jsonString.replace("'", "");
			model.addAttribute("jsonString", jsonString);
		} catch (Exception e)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.json.writing"));
		}
		
		return new ModelAndView("quizPage", model);
	}
	
	
}
