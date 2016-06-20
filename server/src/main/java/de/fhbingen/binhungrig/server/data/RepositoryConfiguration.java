package de.fhbingen.binhungrig.server.data;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for JPA/Hibernate
 */
@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"de.fhbingen.binhungrig.server.data"})
@EnableJpaRepositories(basePackages = {"de.fhbingen.binhungrig.server.data"})
@EnableTransactionManagement
public class RepositoryConfiguration {
	@Value("${database.hbm2ddl.auto}")
	private String hbm2ddlAuto;
	
	@Value("${database.show_sql")
	private String hibernateShowSql;
	
	@Value("${database.driverClassName")
	private String driverClassName;
	
	//@Value("${spring.jpa.hibernate.naming_strategy}")
	//private String namingStrategy;
	
	@Autowired
	private DataSource dataSource;
	
	@Configuration
	@Profile("default")
	static class Default {
		
		@Value("${database.url}")
		private String databaseUrl;
		
		@Value("${database.user}")
	    private String databaseUser;
		
		@Value("${database.password}")
	    private String databasePassword;
		
		@Value("${database.driver}")
		private String databaseDriver;
		
		@Bean
		public static PropertyPlaceholderConfigurer jpaPropertyPlaceholderConfigurer() {
			PropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new PropertyPlaceholderConfigurer();
			propertyPlaceholderConfigurer.setLocation(new ClassPathResource("application-model.properties"));
			propertyPlaceholderConfigurer.setIgnoreUnresolvablePlaceholders(true);
			return propertyPlaceholderConfigurer;
		}
		
		@Bean
		public DataSource dataSource() {
			DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
			driverManagerDataSource.setDriverClassName(databaseDriver);
			driverManagerDataSource.setUrl(databaseUrl);
			driverManagerDataSource.setUsername(databaseUser);
			driverManagerDataSource.setPassword(databasePassword);			
			return driverManagerDataSource;
		}
	}
	
	@Bean
	public EntityManagerFactory entityManagerFactory() {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(Boolean.parseBoolean(hbm2ddlAuto));
		vendorAdapter.setShowSql(Boolean.parseBoolean(hibernateShowSql));

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("de.fhbingen.binhungrig.server");
		factory.setDataSource(dataSource);
		factory.afterPropertiesSet();
		
		return factory.getObject();
	}
	
	@Bean
	public HibernateExceptionTranslator hibernateExceptionTranslator() {
		return new HibernateExceptionTranslator();
	}
}