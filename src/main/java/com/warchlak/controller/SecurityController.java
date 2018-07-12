package com.warchlak.controller;

import com.warchlak.DTO.UserDTO;
import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;
import com.warchlak.exceptionHandling.UserAlreadyExistsException;
import com.warchlak.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import java.util.UUID;

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
			
			String appUrl = request.getContextPath();
			userService.registerUser(userDTO, eventPublisher, appUrl);
			
			
			request.setAttribute("success", "Użytkownik został zarejestrowany, " +
					"na pocztę email przesłano link aktywacyjny ważny 24 godziny");
			
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
	public ModelAndView confirmRegistration(ModelMap model, @RequestParam("token") String token)
	{
		ValidationToken validationToken = userService.getValidationToken(token);
		
		if (validationToken == null)
		{
			model.addAttribute("error", true);
			model.addAttribute("message", "Podany token nie istnieje, link może być niepoprawny" +
					"lub jego czas życia wygasł");
		}
		else
		{
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			long currentTime = calendar.getTime().getTime();
			long deltaTime = validationToken.getExpirationDate().getTime() - currentTime;
			
			if (deltaTime <= 0)
			{
				model.addAttribute("error", true);
				model.addAttribute("message", "Czas życia linku wygasł, w celu potwierdzenia konta" +
						"poproś o ponowne wysłanie linku weryfikacyjnego");
			}
			else
			{
				
				User user = validationToken.getUser();
				user.setEnabled(true);
				
				try
				{
					userService.updateUser(user);
				} catch (Exception e)
				{
					model.addAttribute("error", true);
					model.addAttribute("message", "Nie udało się aktywować użytkownika, " +
							"spróbuj ponownie później");
				}
			}
		}
		
		return new ModelAndView("registerConfirmationPage", model);
	}
	
	@RequestMapping("/showResendTokenPage")
	public String showResendTokenPage()
	{
		return "resendTokenPage";
	}
	
	@PostMapping("/resendToken")
	public ModelAndView resendToken(ModelMap model, @RequestParam(value = "email") String email, HttpServletRequest request)
	{
		User user = userService.getUserByEmail(email);
		if (user == null)
		{
			model.addAttribute("error", true);
			model.addAttribute("errorMessage",
					"Nie udało się odnaleźć użytkownika o podanym adresie email");
		}
		else
		{
			try
			{
				String applicationUrl = request.getContextPath();
				String newToken = UUID.randomUUID().toString();
				userService.updateUserToken(email, newToken, applicationUrl);
				
				model.addAttribute("success", true);
			} catch (MailException e)
			{
				model.addAttribute("error", true);
				model.addAttribute("errorMessage", "" +
						"Nie udało się wysłać linku aktywacyjnego, spróbuj ponownie później");
			} catch (Exception e)
			{
				model.addAttribute("error", true);
				model.addAttribute("errorMessage", "" +
						"Wystąpił błąd przy próbie utworzenia linku");
			}
		}
		
		return new ModelAndView("resendTokenPage", model);
	}
	
}
