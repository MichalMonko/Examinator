package com.warchlak.controller;

import com.warchlak.exceptionHandling.ErrorCodeMessageResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ErrorController
{
	
	private ErrorCodeMessageResolver errorMessageResolver;
	
	@Autowired
	public ErrorController(ErrorCodeMessageResolver errorMessageResolver)
	{
		this.errorMessageResolver = errorMessageResolver;
	}
	
	@RequestMapping("/error")
	public ModelAndView showErrorPage(HttpServletRequest request)
	{
		ModelMap model = new ModelMap();
		int errorCode = (int) request.getAttribute("javax.servlet.error.status_code");
		model.addAttribute("errorCode", errorCode + " " + errorMessageResolver.getErrorCause(errorCode));
		model.addAttribute("errorMessage", errorMessageResolver.resolveErrorMessage(errorCode));
		
		return new ModelAndView("errorPage", model);
	}
}
