package com.warchlak.controller;

import com.warchlak.DTO.UserDTO;
import com.warchlak.config.security.AuthenticationTracker;
import com.warchlak.entity.User;
import com.warchlak.entity.UserPendingPassword;
import com.warchlak.entity.ValidationToken;
import com.warchlak.exceptionHandling.Exception.UserAlreadyExistsException;
import com.warchlak.messages.CustomMessageSource;
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
	
	final private CustomMessageSource messageSource;
	
	
	@Autowired
	public SecurityController(UserServiceInterface userService, CustomMessageSource messageSource)
	{
		this.userService = userService;
		this.messageSource = messageSource;
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
	                             @RequestParam(value = "errorMessage", required = false) String errorMessage)
	{
		return "signupPage";
	}
	
	@PostMapping("/registerUser")
	public ModelAndView registerUser(@Valid UserDTO userDTO, BindingResult bindingResult,
	                                 ModelMap model, HttpServletRequest request)
	{
		if (bindingResult.hasErrors())
		{
			return new ModelAndView("signupPage", model);
		}
		else if (!userDTO.getPassword().equals(userDTO.getConfirmedPassword()))
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.password.notTheSame"));
		}
		else
		{
			try
			{
				String appUrl = request.getContextPath();
				userService.registerUser(userDTO, appUrl);
				
				model.addAttribute("successMessage", messageSource.getMessage("success.registered.emailSent"));
				
			} catch (UserAlreadyExistsException e)
			{
				model.addAttribute("errorMessage", messageSource.getMessage("error.registered.userExists"));
			} catch (Exception e)
			{
				System.out.println(e.getMessage());
				model.addAttribute("errorMessage", messageSource.getMessage("error.registered.other"));
			}
		}
		
		return new ModelAndView("signupPage", model);
	}
	
	@RequestMapping("/confirmRegistration/{token}")
	public ModelAndView confirmRegistration(ModelMap model, @PathVariable("token") String token)
	{
		ValidationToken validationToken = userService.getValidationToken(token);
		
		if (validationToken == null)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidToken"));
		}
		else if (validationToken.getTokenType() != ValidationToken.TOKEN_TYPE.REGISTER)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidTokenType"));
		}
		else
		{
			
			Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
			long currentTime = calendar.getTime().getTime();
			long deltaTime = validationToken.getExpirationDate().getTime() - currentTime;
			
			if (deltaTime <= 0)
			{
				model.addAttribute("errorMessage", messageSource.getMessage("error.registered.tokenExpired"));
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
					model.addAttribute("message", messageSource.getMessage("error.registered.activationOther"));
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
			model.addAttribute("errorMessage",
					messageSource.getMessage("error.registered.userEmailNotFound"));
		}
		else if (user.isEnabled())
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.registered.userAlreadyActive"));
		}
		else
		{
			try
			{
				String applicationUrl = request.getContextPath();
				String newToken = UUID.randomUUID().toString();
				userService.updateUserToken(email, newToken);
				userService.resendUserToken(user, newToken, applicationUrl);
				
				model.addAttribute("success", true);
			} catch (MailException e)
			{
				model.addAttribute("errorMessage", messageSource.getMessage("error.tokenResendError"));
			} catch (Exception e)
			{
				model.addAttribute("errorMessage", messageSource.getMessage("error.tokenLinkCreationError"));
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
	                                           ModelMap model, HttpServletRequest request)
	{
		if (!newPassword.equals(newPasswordConfirm))
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.password.notTheSame"));
		}
		else
		{
			try
			{
				User user = userService.getUserByUsername(AuthenticationTracker.getLoggedUsername());
				if (user == null)
				{
					model.addAttribute("errorMessage", messageSource.getMessage("error.userNotFound"));
					return new ModelAndView("accountPage", model);
				}
				
				String applicationUrl = request.getContextPath();
				String newToken = UUID.randomUUID().toString();
				
				userService.updateUserToken(user, newToken, ValidationToken.TOKEN_TYPE.CHANGE_PASSWORD);
				userService.saveUserPendingPassword(user, newPassword);
				userService.sendPasswordChangeConfirmationLink(user, newToken, applicationUrl);
				
				model.addAttribute("successMessage", messageSource.getMessage("success.passwordChangedEmailSent"));
			} catch (Exception e)
			{
				model.addAttribute("errorMessage", messageSource.getMessage("error.changePasswordErrorOther"));
			}
		}
		return new ModelAndView("accountPage", model);
	}
	
	@PostMapping("/removeUser")
	public ModelAndView sendRemoveUserLink(ModelMap model, HttpServletRequest request)
	{
		User user = userService.getUserByUsername(AuthenticationTracker.getLoggedUsername());
		if (user == null)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.userNotFound"));
			return new ModelAndView("accountPage", model);
		}
		
		try
		{
			String applicationUrl = request.getContextPath();
			String newToken = UUID.randomUUID().toString();
			
			userService.updateUserToken(user, newToken, ValidationToken.TOKEN_TYPE.REMOVE_ACCOUNT);
			userService.sendAccountRemovalConfirmationEmail(user, newToken, applicationUrl);
			model.addAttribute("successMessage", messageSource.getMessage("success.userAccountRemovalEmailSent"));
			
		} catch (Exception e)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.userRemoveOther"));
		}
		return new ModelAndView("accountPage", model);
	}
	
	@RequestMapping("/changePassword/{token}")
	public ModelAndView changePassword(@PathVariable("token") String token,
	                                   ModelMap model)
	{
		ValidationToken validationToken = userService.getValidationToken(token);
		if (validationToken == null)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidToken"));
		}
		else if (validationToken.getTokenType() != ValidationToken.TOKEN_TYPE.CHANGE_PASSWORD)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidTokenType"));
		}
		else
		{
			User user = validationToken.getUser();
			UserPendingPassword userPendingPassword = userService.getUserPendingPassword(user);
			user.setPassword(userPendingPassword.getPendingPassword());
			userService.updateUser(user);
			model.addAttribute("successMessage", messageSource.getMessage("success.passwordChanged"));
		}
		
		return new ModelAndView("accountPage", model);
	}
	
	@RequestMapping("/removeUser/{token}")
	public ModelAndView removeUser(@PathVariable("token") String token,
	                               ModelMap model)
	{
		ValidationToken validationToken = userService.getValidationToken(token);
		if (validationToken == null)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidToken"));
		}
		else if (validationToken.getTokenType() != ValidationToken.TOKEN_TYPE.REMOVE_ACCOUNT)
		{
			model.addAttribute("errorMessage", messageSource.getMessage("error.invalidTokenType"));
		}
		else
		{
			try
			{
				User user = validationToken.getUser();
				userService.removeUser(user);
				model.addAttribute("successMessage", messageSource.getMessage("success.userRemoval"));
			} catch (Exception e)
			{
				model.addAttribute("errorMessage", messageSource.getMessage("error.userRemoveOther"));
			}
		}
		
		return new ModelAndView("loginPage", model);
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
