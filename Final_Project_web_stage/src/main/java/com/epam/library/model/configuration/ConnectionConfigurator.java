package com.epam.library.model.configuration;

import com.epam.library.util.PropertiesExtractor;
import com.epam.library.util.constant.BeanNameHolder;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;

@Configuration
@Profile(value = BeanNameHolder.DEVELOPING_PROFILE)
@Slf4j
public class ConnectionConfigurator {

    private final static String DATA_SOURCE_PROPERTIES = "src/main/resources/properties/dataSource.properties";
    private final static String HIBERNATE_PROPERTIES = "src/main/resources/properties/hibernate.properties";

    @Bean(value = BeanNameHolder.DATA_SOURCE_BEAN, destroyMethod = "close")
    public DataSource getDataSource() {
        HikariConfig hikariConfig = new HikariConfig(DATA_SOURCE_PROPERTIES);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(BeanNameHolder.ENTITY_MANAGER_FACTORY_BEAN)
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        log.info("ENTITY_MANAGER_FACTORY_BEAN: " + this.getClass().getSimpleName());
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(getDataSource());
        em.setPackagesToScan(BeanNameHolder.BASE_PACKAGE);
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(PropertiesExtractor.getProperties(HIBERNATE_PROPERTIES));
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        // em.afterPropertiesSet();
        return em;
    }

    @Bean(BeanNameHolder.TRANSACTION_MANAGER_BEAN)
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


}
