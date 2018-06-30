package com.warchlak.config.mvc;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.logging.Logger;

@Configuration
@ComponentScan(basePackages = "com.warchlak")
@EnableTransactionManagement
@EnableWebMvc
@PropertySource("classpath: AuthenticationDataSourceProperties.prop")
public class AuthenticationDataSourceConfig
{
	private final static Logger LOGGER = Logger.getLogger(AuthenticationDataSourceConfig.class.getName());
	
	final private Environment environment;
	
	@Autowired
	public AuthenticationDataSourceConfig(Environment environment)
	{
		this.environment = environment;
	}
	
	private ComboPooledDataSource getUserDataSource()
	{
		ComboPooledDataSource userDataSource = new ComboPooledDataSource();
		
		try
		{
			userDataSource.setUser(environment.getProperty("jdbc_username"));
			userDataSource.setPassword(environment.getProperty("jdbc_password"));
			userDataSource.setJdbcUrl(environment.getProperty("jdbc_url"));
			userDataSource.setDriverClass(environment.getProperty("jdbc_driver"));
			
			userDataSource.setMaxPoolSize(Integer.parseInt(environment.getProperty("maxConnectionPool")));
			userDataSource.setMinPoolSize(Integer.parseInt(environment.getProperty("minConnectionPool")));
			userDataSource.setInitialPoolSize(Integer.parseInt(environment.getProperty("defaultConnectionPool")));
			userDataSource.setMaxIdleTime(Integer.parseInt(environment.getProperty("maxIdleTime")));
			
			
		} catch (Exception e)
		{
			LOGGER.info("Exception during dataSourceIntialization: " + e.getMessage());
		}
		
		return userDataSource;
	}
	
	private Properties getHibernateProperties()
	{
		Properties properties = new Properties();
		
		properties.setProperty("hibernate.dialect", environment.getProperty("hibernate_dialect_class"));
		properties.setProperty("hibernate.show_sql", environment.getProperty("hibernate_showSQL"));
		
		return properties;
	}
	
	@Bean("userTransactionManager")
	@Autowired
	public HibernateTransactionManager getTransactionManager(@Qualifier("userSessionFactory") SessionFactory sessionFactory)
	{
		HibernateTransactionManager transactionManager = new HibernateTransactionManager();
		
		transactionManager.setSessionFactory(sessionFactory);
		
		return transactionManager;
	}
	
	@Bean("userSessionFactory")
	public LocalSessionFactoryBean getSessionFactory()
	{
		DataSource dataSource = getUserDataSource();
		
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		sessionFactory.setDataSource(dataSource);
		sessionFactory.setPackagesToScan(environment.getProperty("hibernate_packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
}
//#connectionProperties
//		minConnectionPool=5
//		maxConnectionPool=20
//		defaultConnectionPool=5
//		maxIdleTime=5000
//
//		#JDBC Properties
//		jdbc_driverClass=com.mysql.jdbc.Driver
//		jdbc_url=jdbc:mysql://localhost:3306/user_storage?useSSL=false
//		jdbc_username=root
//		jdbc_password=root
//
//		#Hibernate Properties
//		hibernate_dialect_class=org.hibernate.dialect.MySQLDialect
//		hibernate_showSQL=true
//		hibernate_packagesToScan=com.warchlak.entity