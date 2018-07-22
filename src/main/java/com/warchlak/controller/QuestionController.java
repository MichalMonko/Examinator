package com.warchlak.controller;

import com.warchlak.entity.Answer;
import com.warchlak.entity.Course;
import com.warchlak.entity.Question;
import com.warchlak.service.QuestionServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/question")
public class QuestionController
{
	private QuestionServiceInterface questionService;
	
	@Autowired
	public QuestionController(QuestionServiceInterface questionService)
	{
		this.questionService = questionService;
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
}
