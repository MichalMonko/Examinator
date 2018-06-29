package com.warchlak.controller;

import org.springframework.stereotype.Controller;
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
}
