package com.warchlak.controller;

import com.warchlak.DTO.UserDTO;
import com.warchlak.config.security.AuthenticationTracker;
import com.warchlak.entity.User;
import com.warchlak.entity.ValidationToken;
import com.warchlak.exceptionHandling.UserAlreadyExistsException;
import com.warchlak.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.Collection;
import java.util.TimeZone;
import java.util.UUID;

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
			userService.registerUser(userDTO, appUrl);
			
			
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
	
	@RequestMapping("/confirmRegistration/{token}")
	public ModelAndView confirmRegistration(ModelMap model, @PathVariable("token") String token)
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
	public String ResendTokenPage()
	{
		return "tokenResendPage";
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
		
		return new ModelAndView("tokenResendPage", model);
	}
	
	@RequestMapping("/account")
	public ModelAndView showAccountControlPage(ModelMap model)
	{
		String username = AuthenticationTracker.getLoggedUsername();
		Collection<? extends GrantedAuthority> userRoles = AuthenticationTracker.getLoggedUserRoles();
		
		model.addAttribute("username", username);
		model.addAttribute("userRole", determineUserRole(userRoles));
		
		return new ModelAndView("accountPage", model);
	}
	
	@PostMapping("/changePassword")
	public ModelAndView sendChangePasswordLink(@RequestParam("newPassword") String newPassword,
	                                           @RequestParam("newPasswordConfirm") String newPasswordConfirm,
	                                           ModelMap model)
	{
		if (!newPassword.equals(newPasswordConfirm))
		{
			model.addAttribute("error", true);
			model.addAttribute("message", "Hasła muszą być identyczne!");
		}
		else
		{
			User user = userService.getUserByUsername(AuthenticationTracker.getLoggedUsername());
			// TODO: send confirmation email
			model.addAttribute("success", true);
			model.addAttribute("message", "Wysłano link weryfikujący, sprawdź pocztę w celu zmiany hasła");
		}
		return new ModelAndView("accountPage", model);
	}
	
	@PostMapping("/removeUser")
	public ModelAndView sendRemoveUserLink(ModelMap model)
	{
		User user = userService.getUserByUsername(AuthenticationTracker.getLoggedUsername());
		//TODO: send confirmation email
		
		return new ModelAndView("accountPage", model);
	}
	
	@PostMapping("/changePassword/{token}")
	public ModelAndView changePassword(@PathVariable("token") String token,
	                                   ModelMap model)
	{
		//TODO: Change user password if token valid
		return new ModelAndView();
	}
	
	@PostMapping("/removeUser/{token}")
	public ModelAndView removeUser(@PathVariable("token") String token,
	                                   ModelMap model)
	{
		//TODO: Remove User if token valid
		return new ModelAndView();
	}
	
	private String determineUserRole(Collection<? extends GrantedAuthority> userRoles)
	{
		if (userRoles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))
		{
			return "ADMIN";
		}
		
		if (userRoles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_CONTRIBUTOR")))
		{
			return "KONTRYBUTOR";
		}
		
		if (userRoles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER")))
		{
			return "STUDENT";
		}
		
		return "";
	}
	
}
