package com.warchlak.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	
	private final DataSource userDataSource;
	
	@Autowired
	public WebSecurityConfig(DataSource userDataSource)
	{
		this.userDataSource = userDataSource;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.jdbcAuthentication().dataSource(userDataSource)
		.authoritiesByUsernameQuery("select username,password,email,enabled from users where username=?");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.authorizeRequests()
		    .antMatchers("/resources/**").permitAll()
		    .antMatchers("/*").permitAll()
		    .antMatchers("/authentication/signUp").permitAll()
		    .antMatchers("/authentication/registerUser").permitAll()
		    .antMatchers("/authentication/confirmRegistration").permitAll()
		    .antMatchers("/authentication/showResendTokenPage").permitAll()
		    .antMatchers("/authentication/resendToken").permitAll()
		    .antMatchers("/question/**").access("hasRole('ADMIN') or hasRole('CONTRIBUTOR')")
		    .anyRequest()
		    .authenticated()
		    .and()
		    .formLogin()
		    .loginPage("/authentication/login")
		    .permitAll()
		    .and()
		    .httpBasic()
		    .and()
		    .exceptionHandling()
		    .accessDeniedPage("/authentication/denied")
		    .and()
		    .logout()
		    .logoutSuccessUrl("/authentication/logoutSuccess")
		    .permitAll();
	}
}
