package com.warchlak.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, proxyTargetClass = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter
{
	@Configuration
	@Order(2)
	public static class FormSecurityConfig extends WebSecurityConfigurerAdapter
	{
		private final DataSource userDataSource;
		
		@Autowired
		public FormSecurityConfig(DataSource userDataSource)
		{
			this.userDataSource = userDataSource;
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
			    .antMatchers("/question/quiz/*").permitAll()
			    .antMatchers("/question/**").access("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CONTRIBUTOR')")
			    .antMatchers("/resources/**").permitAll()
			    .anyRequest().authenticated()
			    .and()
			    .formLogin()
			    .loginPage("/authentication/login")
			    .permitAll()
			    .and()
			    .logout()
			    .logoutSuccessUrl("/authentication/logoutSuccess")
			    .permitAll();
		}
		
	}
	
	@Configuration
	@Order(1)
	public static class RestSecurityConfig extends WebSecurityConfigurerAdapter
	{
		private final DataSource userDataSource;
		
		@Autowired
		private RestEntryPoint entryPoint;
		
		@Autowired
		public RestSecurityConfig(DataSource userDataSource)
		{
			this.userDataSource = userDataSource;
		}
		
		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception
		{
			auth.jdbcAuthentication().dataSource(userDataSource);
		}
		
		@Override
		protected void configure(HttpSecurity http) throws Exception
		{
			http.antMatcher("/api/**")
			    .authorizeRequests()
			    .requestMatchers(new AntPathRequestMatcher("/api/**", "GET")).permitAll()
			    .requestMatchers(new AntPathRequestMatcher("/api/**", "POST"))
			    .access("hasAuthority('ROLE_ADMIN') or hasAuthority('ROLE_CONTRIBUTOR')")
			    .requestMatchers(new AntPathRequestMatcher("/api/**", "DELETE"))
			    .access("hasAuthority('ROLE_ADMIN')").
					    and()
			    .httpBasic().authenticationEntryPoint(entryPoint);
		}
	}
	
	
}


