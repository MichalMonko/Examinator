package com.warchlak.controller;

import com.warchlak.DTO.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authentication")
public class SecurityController
{
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
	
	@RequestMapping("/singup")
	public String showSingupPage(@ModelAttribute("userDTO") UserDTO userDTO) {
		return "singupPage";
	}
	
	@PostMapping("/register")
	public String registerUser(@ModelAttribute("userDTO")UserDTO userDTO)
	{
		if(!userDTO.getPassword().equals(userDTO.getConfirmedPassword()))
		{
			return "pass dont match";
		}
		return "placeholder";
	}
	
}
