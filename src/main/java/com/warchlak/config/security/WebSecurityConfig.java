package com.warchlak.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	private final DataSource userDataSource;
	
	private AccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
	public WebSecurityConfig(DataSource userDataSource, AccessDeniedHandler accessDeniedHandler)
	{
		this.userDataSource = userDataSource;
		this.customAccessDeniedHandler = accessDeniedHandler;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(userDataSource);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		    .antMatchers("/authentication/signUp").permitAll()
		    .antMatchers("/authentication/registerUser").permitAll()
		    .antMatchers("/authentication/confirmRegistration/*").permitAll()
		    .antMatchers("/authentication/removeUser/*").permitAll()
		    .antMatchers("/authentication/showResendTokenPage").permitAll()
		    .antMatchers("/authentication/resendToken").permitAll()
		    .antMatchers("/*").permitAll()
		    .antMatchers("/authentication/account").access("hasAnyAuthority('ROLE_USER','ROLE_ADMIN','ROLE_CONTRIBUTOR')")
		    .antMatchers("/question/**").access("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CONTRIBUTOR')")
		    .antMatchers("/resources/**").permitAll()
		    .requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).permitAll()
		    .requestMatchers(new AntPathRequestMatcher("/api/**", "POST"))
		    .access("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CONTRIBUTOR')")
		    .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE"))
		    .access("hasAuthority('ROLE_ADMIN')")
		    .anyRequest()
		    .authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/authentication/login")
		    .permitAll()
		    .and()
		    .httpBasic()
		    .and()
		    .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler)
		    .and()
		    .logout()
		    .logoutSuccessUrl("/authentication/logoutSuccess")
		    .permitAll();
	}
}
