package com.warchlak.config.mvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = "com.warchlak")
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("classpath:dataSourceProperties.prop")
public class MvcConfiguration implements WebMvcConfigurer
{
	
	private static final Logger LOGGER = Logger.getLogger(MvcConfiguration.class.getName());
	
	final private Environment environment;
	
	@Autowired
	public MvcConfiguration(Environment environment)
	{
		this.environment = environment;
	}
	
	@Bean
	InternalResourceViewResolver getViewResolver()
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		return resolver;
	}
	
	@Bean
	public JavaMailSender getJavaMailSender()
	{
		
		String emailPassword = "";
		
		//File will be hidden on github
		try
		{
			Resource resource = new ClassPathResource("/emailPasswords.prop");
			Properties properties = PropertiesLoaderUtils.loadProperties(resource);
			emailPassword = properties.getProperty("email.password");
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		
		mailSender.setUsername("testownikiPWR@gmail.com");
		mailSender.setPassword(emailPassword);
		
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.debug", "true");
		
		return mailSender;
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry)
	{
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	private ComboPooledDataSource getDataSource()
	{
		
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		try
		{
			int maxPoolSize = convertStringPropertyToInt(environment.getProperty("maxPoolSize"));
			int minPoolSize = convertStringPropertyToInt(environment.getProperty("minPoolSize"));
			int initialPoolSize = convertStringPropertyToInt(environment.getProperty("initialPoolSize"));
			
			dataSource.setMaxPoolSize(maxPoolSize);
			dataSource.setMinPoolSize(minPoolSize);
			dataSource.setInitialPoolSize(initialPoolSize);
			
			dataSource.setDriverClass(environment.getProperty("driverClass"));
			dataSource.setJdbcUrl(environment.getProperty("url"));
			
			dataSource.setUser(environment.getProperty("user"));
			dataSource.setPassword(environment.getProperty("password"));
			
		} catch (Exception e)
		{
			LOGGER.info("Exception: " + e.getMessage());
		}
		
		return dataSource;
	}
	
	private Properties getHibernateProperties()
	{
		Properties properties = new Properties();
		
		properties.setProperty("hibernate.dialect", environment.getProperty("dialectClass"));
		properties.setProperty("hibernate.show_sql", environment.getProperty("showSql"));
		
		return properties;
	}
	
	@Primary
	@Bean
	public LocalSessionFactoryBean getSessionFactory(DataSource dataSource)
	{
		dataSource = getDataSource();
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		sessionFactory.setHibernateProperties(getHibernateProperties());
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(environment.getProperty("packagesToScan"));
		
		return sessionFactory;
	}
	
	@Primary
	@Bean
	@Autowired
	public HibernateTransactionManager getHibernateTransactionManager(SessionFactory sessionFactory)
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
	
	private int convertStringPropertyToInt(String propertyValue) throws Exception
	{
		return Integer.parseInt(propertyValue);
	}
	
	
}
