package com.warchlak.config.mvc;

import com.warchlak.config.security.AuthenticationDataSourceConfig;
import com.warchlak.config.security.WebSecurityConfig;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class SpringMvcDispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return new Class[]{WebSecurityConfig.class, AuthenticationDataSourceConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class[]{MvcConfiguration.class,};
	}
	
	@Override
	protected String[] getServletMappings()
	{
		return new String[]{"/"};
	}
	
	@Override
	protected Filter[] getServletFilters()
	{
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		return new Filter[]{characterEncodingFilter};
	}
}
