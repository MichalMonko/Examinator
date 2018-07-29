package com.warchlak.config.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) throws IOException, ServletException
	{
		String apiPattern = "/api/.*";
		String requestPath = request.getServletPath();
		
		if (requestPath.matches(apiPattern))
		{
			response.sendRedirect("/api/unauthorized_access");
		}
		else
		{
			throw accessDeniedException;
		}
	}
}
