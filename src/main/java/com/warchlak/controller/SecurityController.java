package com.warchlak.controller;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;
import com.warchlak.events.UserRegistrationEvent;
import com.warchlak.exceptionHandling.UserAlreadyExistsException;
import com.warchlak.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.TimeZone;

@Controller
@RequestMapping("/authentication")
public class SecurityController
{
	final private UserServiceInterface userService;
	private ApplicationEventPublisher eventPublisher;
	
	@Autowired
	public SecurityController(UserServiceInterface userService, ApplicationEventPublisher eventPublisher)
	{
		this.userService = userService;
		this.eventPublisher = eventPublisher;
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
	                             @RequestParam(name = "error", required = false) String errorMessage)
	{
		return "signupPage";
	}
	
	@PostMapping("/registerUser")
	public String registerUser(@Valid UserDTO userDTO, BindingResult bindingResult,
	                           HttpServletRequest request)
	{
		if (bindingResult.hasErrors())
		{
			return "signupPage";
		}
		if (!userDTO.getPassword().equals(userDTO.getConfirmedPassword()))
		{
			request.setAttribute("error", "Hasła nie są identyczne!");
			return "signupPage";
		}
		
		try
		{
			
			User registeredUser = userService.saveUser(userDTO);
			
			String appUrl = request.getContextPath();
			eventPublisher.publishEvent(new UserRegistrationEvent(registeredUser, appUrl));
			
			request.setAttribute("success", "Użytkownik został zarejestrowany");
			
		} catch (UserAlreadyExistsException e)
		{
			request.setAttribute("error", "Użytkownik o podanym adresie email lub loginie już istnieje.");
		} catch (Exception e)
		{
			System.out.println(e.getMessage());
			request.setAttribute("error", "Wystąpił błąd przy dodawania użytkownika, spróbuj ponownie później.");
		}
		
		return "signupPage";
		
	}
	
	@RequestMapping("/confirmRegistration")
	public String confirmRegistration(ModelAndView model, @RequestParam("token") String token)
	{
		ValidationToken validationToken = userService.getValidationToken(token);
		
		if (validationToken == null)
		{
			model.addObject("error", true);
			model.addObject("message", "Podany token nie istnieje, link może być niepoprawny" +
					"lub jego czas życia wygasł");
			return "registerConfirmationPage";
		}
		
		Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		long currentTime = calendar.getTime().getTime();
		long deltaTime = validationToken.getExpirationDate().getTime() - currentTime;
		
		if (deltaTime <= 0)
		{
			model.addObject("error", true);
			model.addObject("message", "Czas życia linku wygasł, w celu potwierdzenia konta" +
					"poproś o ponowne wysłanie linku weryfikacyjnego");
			return "registerConfirmationPage";
		}
		
		User user = validationToken.getUser();
		user.setEnabled(true);
		
		try
		{
			userService.updateUser(user);
		} catch (Exception e)
		{
			model.addObject("error", true);
			model.addObject("message", "Nie udało się aktywować użytkownika, " +
					"spróbuj ponownie później");
		}
		
		return "registerConfirmationPage";
		
		
	}
	
	
}
