package com.warchlak.config.mvc;

import com.warchlak.config.security.AuthenticationDataSourceConfig;
import com.warchlak.config.security.WebSecurityConfig;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class[] {WebSecurityConfig.class, AuthenticationDataSourceConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class[]{MvcConfiguration.class, };
	}
	
	@Override
	protected String[] getServletMappings()
	{
		return new String[]{"/"};
	}
}
