package com.epam.library.model.configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Profile(value = {"dev"})
@Slf4j
public class ConnectionConfigurator {

	public final static String BASE_PACKAGE = "com.epam.library";

	public static final String DATA_SOURCE_BEAN = "dataSource";
	public static final String TRANSACTION_MANAGER_BEAN = "transactionManager";
	public static final String ENTITY_MANAGER_FACTORY_BEAN = "entityManagerFactory";
	public static final String FLYWAY_BEAN = "flyway";
	public static final String DEVELOPING_PROFILE = "dev";
	public static final String TESTING_PROFILE = "test";
	
	private final static String DATA_SOURCE_PROPERTIES = "src/resources/properties/dataSource.properties";
	private final static String HIBERNATE_PROPERTIES = "src/resources/properties/hibernate.properties";

	@Bean(value = DATA_SOURCE_BEAN, destroyMethod = "close")
	public DataSource getDataSource() {
		HikariConfig hikariConfig = new HikariConfig(DATA_SOURCE_PROPERTIES);
		return new HikariDataSource(hikariConfig);
	}

	@Bean(ENTITY_MANAGER_FACTORY_BEAN)
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		log.info("ENTITY_MANAGER_FACTORY_BEAN: " + this.getClass().getSimpleName());
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(getDataSource());
		em.setPackagesToScan(BASE_PACKAGE);
		em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		em.setJpaProperties(getProperties(HIBERNATE_PROPERTIES));
		em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
		return em;
	}

	@Bean(TRANSACTION_MANAGER_BEAN)
	public JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return transactionManager;
	}
	
	private Properties getProperties(String propertiesFileLOCATION) {
		Properties properties = new Properties();
		try (InputStream inputStream = new FileInputStream(propertiesFileLOCATION)) {
			properties.load(inputStream);
		} catch (IOException e) {
			log.error("Unable to read Property file: " + propertiesFileLOCATION, e);
		}
		return properties;
	}

}
