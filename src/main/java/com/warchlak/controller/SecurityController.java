package com.warchlak.controller;

import com.warchlak.DTO.UserDTO;
import com.warchlak.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@RequestMapping("/authentication")
public class SecurityController
{
	final private UserServiceInterface userService;
	
	@Autowired
	public SecurityController(UserServiceInterface userService)
	{
		this.userService = userService;
	}
	
	@RequestMapping("/login")
	public String showLoginForm()
	{
		return "loginPage";
	}
	
	@RequestMapping("/denied")
	public String showDeniedPage()
	{
		return "accessDeniedPage";
	}
	
	@RequestMapping("/logoutSuccess")
	public String logout()
	{
		return "logoutPage";
	}
	
	@RequestMapping("/signUp")
	public String showSignupPage(@ModelAttribute("userDTO") UserDTO userDTO,
	                             @RequestParam(name = "error", required = false) String errorMessage) {
		return "signupPage";
	}
	
	@PostMapping("/registerUser")
	public String registerUser(@Valid UserDTO userDTO, BindingResult bindingResult,
	                           HttpServletRequest request)
	{
		if(bindingResult.hasErrors())
		{
			request.setAttribute("error", "Form has errors");
			return "signupPage";
		}
		if(!userDTO.getPassword().equals(userDTO.getConfirmedPassword()))
		{
			request.setAttribute("error", "Password doesn't match");
			return "signupPage";
		}
		
		userService.saveUser(userDTO);
		
		return "signupPage";
		
	}
	
}
